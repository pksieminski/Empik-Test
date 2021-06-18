package com.pksieminski.empiktest.userservice.adapter.api;

import com.pksieminski.empiktest.userservice.adapter.data.entity.Statistics;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@Tag("ComponentTest")
@ActiveProfiles("test")
@AutoConfigureWireMock(port = 8888)
@DisplayName("Component End-To-End tests for UserController")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerComponentTest {

    @Container
    static MongoDBContainer mongoDB = new MongoDBContainer("mongo");

    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        String uri = mongoDB.getReplicaSetUrl();
        registry.add("spring.data.mongodb.uri", () -> uri);
    }

    @LocalServerPort
    int serverPort;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ReactiveMongoTemplate template;

    @AfterEach
    public void tearDown() {
        template.dropCollection(Statistics.class)
                .block();
    }

    @Test
    @DisplayName("Happy path End-To-End test for Component UserService API: GET '/users/{login}'")
    public void whenCalledGetUserEndpoint_givenUserExists_shouldReturnProperResponse() {
        // given github-get-user-pksieminski.json external api stub
        webTestClient.get()
                .uri("localhost:" + serverPort + "/users/pksieminski")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(57523979)
                .jsonPath("$.login").isEqualTo("pksieminski")
                .jsonPath("$.name").isEmpty()
                .jsonPath("$.type").isEqualTo("User")
                .jsonPath("$.avatarUrl").isEqualTo("https://avatars.githubusercontent.com/u/57523979?v=4")
                .jsonPath("$.createdAt").isEqualTo("2019-11-08T09:09:47Z")
                .jsonPath("$.calculations").isEqualTo(0.0);
    }

    @Test
    @DisplayName("User not found End-To-End test for Component UserService API: GET '/users/{login}'")
    public void whenCalledGetUserEndpoint_givenUserNotFound_shouldReturnProperResponse() {
        // given github-get-user-pksieminski.json external api stub
        webTestClient.get()
                .uri("localhost:" + serverPort + "/users/notfound")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}

