package com.pksieminski.empiktest.userservice.adapter.api;

import com.pksieminski.empiktest.userservice.adapter.api.mapper.UserToGetUserResponseMapper;
import com.pksieminski.empiktest.userservice.domain.model.User;
import com.pksieminski.empiktest.userservice.domain.port.inbound.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("UnitTest")
@DisplayName("Unit tests for UserApiService")
@ExtendWith(MockitoExtension.class)
public class UserApiServiceUnitTest {

    @Mock
    private UserService userService;

    @Mock
    private UserToGetUserResponseMapper mapper;

    @InjectMocks
    private UserApiService userApiService;


    @Test
    @DisplayName("Should return GetUserResponse when found and Mono not empty")
    public void whenCalledGetUser_givenLogin_thenReturnUser() {
        String login = "TestLogin";
        User user = Mockito.mock(User.class);
        GetUserResponse expected = Mockito.mock(GetUserResponse.class);

        when(userService.getUser(login)).thenReturn(Mono.just(user));
        when(mapper.map(user)).thenReturn(expected);

        Mono<GetUserResponse> actual = userApiService.getUser(login);

        assertThat(actual.block(), is(expected));

        verify(userService, atLeastOnce()).getUser(login);
        verify(mapper, atLeastOnce()).map(user);
    }

    @Test
    @DisplayName("Should return empty Mono when User not found")
    public void whenCalledGetUser_givenLogin_thenReturnEmptyUser() {
        String login = "TestLogin";
        User user = Mockito.mock(User.class);
        GetUserResponse expected = Mockito.mock(GetUserResponse.class);

        when(userService.getUser(login)).thenReturn(Mono.empty());

        Mono<GetUserResponse> actual = userApiService.getUser(login);

        assertThat(actual.block(), is(nullValue()));

        verify(userService, atLeastOnce()).getUser(login);
        verify(mapper, never()).map(user);
    }
}
