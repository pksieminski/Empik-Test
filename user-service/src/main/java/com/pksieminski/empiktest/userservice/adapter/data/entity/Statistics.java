package com.pksieminski.empiktest.userservice.adapter.data.entity;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Value
@Builder
@Document(collection = "statistics")
public class Statistics {

    @Id
    public String id;

    @Field("LOGIN")
    @Indexed(unique = true)
    public String login;

    @Field("REQUEST_COUNT")
    public Long requestCount;
}
