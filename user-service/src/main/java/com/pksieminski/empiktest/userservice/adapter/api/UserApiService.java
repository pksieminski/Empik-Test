package com.pksieminski.empiktest.userservice.adapter.api;

import com.pksieminski.empiktest.userservice.adapter.api.mapper.UserToGetUserResponseMapper;
import com.pksieminski.empiktest.userservice.domain.port.inbound.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserApiService {

    private final UserService userService;
    private final UserToGetUserResponseMapper getUserConverter;

    @Autowired
    public UserApiService(UserService userService, UserToGetUserResponseMapper getUserConverter) {
        this.userService = userService;
        this.getUserConverter = getUserConverter;
    }

    public Mono<GetUserResponse> getUser(String login) {
        return userService.getUser(login)
                .map(getUserConverter::map);
    }
}
