package com.pksieminski.empiktest.userservice.adapter.data.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GitHubUserResponse {

    Long id;
    String login;
    String name;
    String type;
    String avatarUrl;
    ZonedDateTime createdAt;
    Long followers;
    Long publicRepos;
}
