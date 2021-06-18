package com.pksieminski.empiktest.userservice.adapter.api.mapper;

import com.pksieminski.empiktest.userservice.adapter.api.GetUserResponse;
import com.pksieminski.empiktest.userservice.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserToGetUserResponseMapper {

    public GetUserResponse map(User user) {
        return GetUserResponse.builder()
                .id(user.getId())
                .login(user.getLogin())
                .name(user.getName())
                .type(user.getType())
                .avatarUrl(user.getAvatarUrl())
                .createdAt(user.getCreatedAt())
                .calculations(user.getCalculations())
                .build();
    }
}
