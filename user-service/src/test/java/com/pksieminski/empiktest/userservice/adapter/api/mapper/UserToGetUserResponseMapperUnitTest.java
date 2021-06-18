package com.pksieminski.empiktest.userservice.adapter.api.mapper;

import com.pksieminski.empiktest.userservice.adapter.api.GetUserResponse;
import com.pksieminski.empiktest.userservice.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Tag("UnitTest")
@DisplayName("Unit tests for UserToGetUserResponseMapper")
public class UserToGetUserResponseMapperUnitTest {

    private UserToGetUserResponseMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = new UserToGetUserResponseMapper();
    }

    @Test
    @DisplayName("Should properly map User to GetUserResponse")
    public void whenCalledMap_givenUser_thenReturnGetUserResponse() {

        // given
        Long id = 12345L;
        String login = "TestLogin";
        String name = "TestName";
        String type = "TestType";
        String avatarUrl = "https://host/avatar";
        ZonedDateTime createdAt = ZonedDateTime.parse("2021-01-01T12:00:00.123+05:12");
        Double calculations = 123.43;

        User user = User.builder()
                .id(id)
                .login(login)
                .name(name)
                .type(type)
                .avatarUrl(avatarUrl)
                .createdAt(createdAt)
                .calculations(calculations)
                .build();

        // when
        GetUserResponse actual = mapper.map(user);

        // then
        assertThat(actual.getId(), is(id));
        assertThat(actual.getLogin(), is(login));
        assertThat(actual.getName(), is(name));
        assertThat(actual.getType(), is(type));
        assertThat(actual.getAvatarUrl(), is(avatarUrl));
        assertThat(actual.getCreatedAt(), is(createdAt));
        assertThat(actual.getCalculations(), is(calculations));
    }
}
