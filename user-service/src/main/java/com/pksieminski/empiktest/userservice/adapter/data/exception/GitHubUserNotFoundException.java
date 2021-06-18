package com.pksieminski.empiktest.userservice.adapter.data.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GitHubUserNotFoundException extends ResponseStatusException {

    public GitHubUserNotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }
}
