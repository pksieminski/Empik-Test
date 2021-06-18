package com.pksieminski.empiktest.userservice.adapter.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserApiService userApiService;

    public UserController(UserApiService userApiService) {
        this.userApiService = userApiService;
    }

    @CrossOrigin
    @GetMapping("/{login}")
    public Mono<ResponseEntity<GetUserResponse>> get(@PathVariable String login) {
        return userApiService.getUser(login)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
