package ch.bbw.m151.jokesdb.repository;

import ch.bbw.m151.jokesdb.datamodel.JokesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface JokesRepository extends JpaRepository<JokesEntity, Integer> {

    List<JokesEntity> findAllByCreateDateAfter(OffsetDateTime when);

    boolean existsByJoke(String joke);
}
