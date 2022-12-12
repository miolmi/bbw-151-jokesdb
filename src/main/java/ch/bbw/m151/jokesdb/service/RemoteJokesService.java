package ch.bbw.m151.jokesdb.service;

import lombok.Data;
import org.springframework.web.reactive.function.client.WebClient;

public class RemoteJokesService {

    @Data
    public static class JokeDto {
        String joke;
    }

    public JokeDto jotd() {
        var client = WebClient.builder()
                .baseUrl("https://v2.jokesapi.dev")
                .build();
        return client.get()
                .uri("/jokes/Programming?type=taken")
                .retrieve()
                .bodyToMono(JokeDto.class)
                .block();
    }
}
