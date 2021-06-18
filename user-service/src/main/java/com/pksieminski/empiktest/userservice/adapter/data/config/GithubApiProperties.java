package com.pksieminski.empiktest.userservice.adapter.data.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "github.api")
public class GithubApiProperties {

    private String host;
}
