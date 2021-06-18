package com.pksieminski.empiktest.userservice.domain.port.outbound;

import com.pksieminski.empiktest.userservice.domain.model.User;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<User> findByLogin(String login);
}
