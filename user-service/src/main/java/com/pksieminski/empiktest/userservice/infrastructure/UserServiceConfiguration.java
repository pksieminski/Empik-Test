package com.pksieminski.empiktest.userservice.infrastructure;

import com.pksieminski.empiktest.userservice.domain.port.inbound.UserService;
import com.pksieminski.empiktest.userservice.domain.port.outbound.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceConfiguration {

    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserService(userRepository);
    }
}
