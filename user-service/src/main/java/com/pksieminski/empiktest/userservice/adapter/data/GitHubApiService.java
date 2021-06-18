package com.pksieminski.empiktest.userservice.adapter.data;

import com.pksieminski.empiktest.userservice.adapter.data.config.GithubApiProperties;
import com.pksieminski.empiktest.userservice.adapter.data.model.GitHubUserResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class GitHubApiService {

    private final WebClient webClient;

    @Autowired
    public GitHubApiService(WebClient.Builder webClientBuilder, GithubApiProperties config) {
        this.webClient = webClientBuilder.baseUrl(config.getHost())
                .build();
    }

    @Retry(name = "github")
    @CircuitBreaker(name = "github")
    public Mono<GitHubUserResponse> getUserDetails(String login) {
        // Will fail fast on CircuitBreaker or failed Retires - Might implement short Redis cache and serve fallback from cache
        return this.webClient.get()
                .uri("/users/{login}", login)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToMono(GitHubUserResponse.class);
                    } else if (response.statusCode().equals(HttpStatus.NOT_FOUND)) {
                        return Mono.empty();
                    } else {
                        return response.createException().flatMap(Mono::error);
                    }
                });
    }
}
