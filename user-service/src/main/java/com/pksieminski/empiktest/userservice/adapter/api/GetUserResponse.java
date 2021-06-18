package com.pksieminski.empiktest.userservice.adapter.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
@Builder
public class GetUserResponse {

    Long id;
    String login;
    String name;
    String type;
    String avatarUrl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    ZonedDateTime createdAt;
    Double calculations;
}
