package com.pksieminski.empiktest.userservice.adapter.data;

import com.pksieminski.empiktest.userservice.adapter.data.model.GitHubUserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@Tag("IntegrationTest")
@ActiveProfiles("test")
@AutoConfigureWireMock(port = 8888)
@DisplayName("Integration tests for GitHubApiService")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GitHubApiServiceIntegrationTest {

    @Autowired
    GitHubApiService apiService;

    @Test
    public void whenCalledGetUserDetails_givenUserExists_shouldReturnGitHubUserResponse() {
        // given
        String login = "pksieminski";

        // when
        GitHubUserResponse actual = apiService.getUserDetails(login).block();

        // then
        assertThat(actual, is(notNullValue()));
        assertThat(actual.getId(), is(57523979L));
        assertThat(actual.getName(), is(nullValue()));
        assertThat(actual.getType(), is("User"));
        assertThat(actual.getAvatarUrl(), is("https://avatars.githubusercontent.com/u/57523979?v=4"));
        assertThat(actual.getCreatedAt().toString(), is("2019-11-08T09:09:47Z[UTC]"));
        assertThat(actual.getFollowers(), is(0L));
        assertThat(actual.getPublicRepos(), is(0L));
    }

    @Test
    public void whenCalledGetUserDetails_givenUserNotFound_shouldThrowException() {
        // given
        String login = "notfound";

        // when
        GitHubUserResponse actual = apiService.getUserDetails(login).block();

        // then
        assertThat(actual, is(nullValue()));
    }
}
