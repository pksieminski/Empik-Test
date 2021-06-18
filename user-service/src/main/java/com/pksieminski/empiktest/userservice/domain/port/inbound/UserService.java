package com.pksieminski.empiktest.userservice.domain.port.inbound;

import com.pksieminski.empiktest.userservice.domain.model.User;
import com.pksieminski.empiktest.userservice.domain.port.outbound.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> getUser(String login) {
        return this.userRepository.findByLogin(login);
    }
}
