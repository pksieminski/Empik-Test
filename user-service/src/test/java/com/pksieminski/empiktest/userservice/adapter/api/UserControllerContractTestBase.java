package com.pksieminski.empiktest.userservice.adapter.api;

import com.pksieminski.empiktest.userservice.UserServiceApplication;
import io.restassured.module.webtestclient.RestAssuredWebTestClient;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;

@WebFluxTest(controllers = UserController.class)
@ContextConfiguration(classes = UserServiceApplication.class)
public abstract class UserControllerContractTestBase {

        @MockBean
        UserApiService userApiService;

        @Autowired
        UserController userController;

        @BeforeEach
        public void setUp() {

            RestAssuredWebTestClient.standaloneSetup(userController);

            Mockito.when(userApiService.getUser("pksieminski"))
                    .thenReturn(Mono.just(GetUserResponse.builder()
                            .id(57523979L)
                            .login("pksieminski")
                            .name(null)
                            .type("User")
                            .avatarUrl("https://avatars.githubusercontent.com/u/57523979?v=4")
                            .createdAt(ZonedDateTime.parse("2019-11-08T09:09:47.000+00:00"))
                            .calculations(0.0)
                            .build()));
        }
}
