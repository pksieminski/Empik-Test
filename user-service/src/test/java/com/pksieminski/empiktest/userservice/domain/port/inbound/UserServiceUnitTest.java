package com.pksieminski.empiktest.userservice.domain.port.inbound;

import com.pksieminski.empiktest.userservice.domain.model.User;
import com.pksieminski.empiktest.userservice.domain.port.outbound.UserRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("UnitTest")
@DisplayName("Unit tests for UserService")
@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Should return User when found and Mono not empty")
    public void whenCalledGetUser_givenLogin_thenReturnUser() {
        String login = "TestLogin";
        User expected = Mockito.mock(User.class);

        when(userRepository.findByLogin(login)).thenReturn(Mono.just(expected));

        Mono<User> actual = userService.getUser(login);

        assertThat(actual.block(), is(expected));

        verify(userRepository, atLeastOnce()).findByLogin(login);
    }

    @Test
    @DisplayName("Should return empty Mono when not found")
    public void whenCalledGetUser_givenLogin_thenReturnEmptyUser() {
        String login = "TestLogin";

        when(userRepository.findByLogin(login)).thenReturn(Mono.empty());

        Mono<User> actual = userService.getUser(login);

        assertThat(actual.block(), is(nullValue()));

        verify(userRepository, atLeastOnce()).findByLogin(login);

    }
}
