package ch.bbw.m151.jokesdb.service;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import ch.bbw.m151.jokesdb.datamodel.JokesDto;
import ch.bbw.m151.jokesdb.datamodel.JokesEntity;
import ch.bbw.m151.jokesdb.repository.JokesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JokesService {
	private final JokesRepository jokesRepository;
	private final RemoteJokesService remoteJokeService;

	@EventListener(ContextRefreshedEvent.class)
	public void preloadDatabase() {
		if (jokesRepository.count() != 0) {
			log.info("database already contains data...");
			return;
		}
		log.info("will load jokes from classpath...");
		try (Stream<String> lineStream = Files.lines(new ClassPathResource("chucknorris.txt").getFile()
				.toPath(), StandardCharsets.UTF_8)) {
			List<JokesEntity> jokes = lineStream.filter(x -> !x.isEmpty())
					.map(x -> new JokesEntity().setJoke(x))
					.toList();
			jokesRepository.saveAll(jokes);
		} catch (IOException e) {
			throw new RuntimeException("failed reading jokes from classpath", e);
		}
	}

	public JokesEntity addJoke(JokesEntity joke) {
		if (!jokesRepository.existsByJoke(joke.getJoke())) {
			log.info("saving joke '{}' to db", joke.getJoke());
			return jokesRepository.save(joke);
		} else {
			log.warn("joke '{}' already exists", joke.getJoke());
			return null;
		}
	}

	public JokesEntity saveJokeToDB() {
		RemoteJokesService.JokeDto jotd = remoteJokeService.jotd();
		JokesEntity jokesEntity = new JokesEntity();
		jokesEntity.setJoke(jotd.getJoke());
		return this.addJoke(jokesEntity);
	}

	public List<JokesEntity> getAllJokes(Pageable pageable) {
		log.info("loading all jokes");
		return jokesRepository.findAll(pageable)
				.getContent();
	}

	public JokesEntity getJokesById(Integer id) throws Exception {
		Optional<JokesEntity> joke = jokesRepository.findById(id);
		if (joke.isPresent()) {
			log.info("getting joke by id {}", id);
			return joke.get();
		} else {
			log.warn("joke with id {} not found", id);
			throw new Exception();
		}
	}

	public JokesEntity updateJoke(Integer id, JokesDto joke) throws Exception {
		Optional<JokesEntity> jokeOptional = jokesRepository.findById(id);
		if (jokeOptional.isPresent()) {
			JokesEntity existingJoke = jokeOptional.get();
			existingJoke.setJoke(joke.getJoke());
			log.info("updating joke with id {}", id);
			return jokesRepository.save(existingJoke);
		} else {
			log.warn("joke with id {} not found", id);
			throw new Exception();
		}
	}

	public void deleteJoke(Integer id) {
		if (!jokesRepository.existsById(id)) {
			log.warn("joke with id {} not found", id);
		} else {
			log.info("deleting joke with id {}", id);
			jokesRepository.deleteById(id);
		}
	}

	public void rateJoke(Integer id, String rating) {
		Optional<JokesEntity> jokeOptional = jokesRepository.findById(id);
		if (jokeOptional.isPresent()) {
			JokesEntity existingJoke = jokeOptional.get();
			existingJoke.rateJoke(rating);
			log.info("rating joke with id {}", id);
			jokesRepository.save(existingJoke);
		} else {
			log.warn("joke with id {} not found", id);
		}
	}
}