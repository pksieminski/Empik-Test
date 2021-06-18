package com.pksieminski.empiktest.userservice.adapter.data;

import com.pksieminski.empiktest.userservice.adapter.data.entity.Statistics;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
@Testcontainers
@Tag("IntegrationTest")
@DisplayName("Integration tests for StatisticsRepository")
public class StatisticsRepositoryIntegrationTest {

    @Container
    static MongoDBContainer mongoDB = new MongoDBContainer("mongo");

    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        String uri = mongoDB.getReplicaSetUrl();
        registry.add("spring.data.mongodb.uri", () -> uri);
    }

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private ReactiveMongoTemplate template;

    @AfterEach
    public void tearDown() {
        template.dropCollection(Statistics.class)
                .block();
    }

    @Test
    public void whenCalledIncrementRequestCount_givenFirstTime_documentCreatedAndReturned() {
        // given
        String login = "TestLogin";

        // when
        Statistics actual = statisticsRepository.incrementRequestCount(login)
                .block();

        // then
        assertThat(actual, is(notNullValue()));
        assertThat(actual.getId(), is(not(blankOrNullString())));
        assertThat(actual.getLogin(), is(login));
        assertThat(actual.getRequestCount(), is(1L));
    }

    @Test
    public void whenCalledIncrementRequestCount_givenFirstTime_documentCreatedAndFetched() {
        // given
        String login = "TestLogin";

        // when
        statisticsRepository.incrementRequestCount(login)
                .block();

        Statistics actual = template.find(new Query(Criteria.where("LOGIN")
                .is(login)), Statistics.class)
                .blockFirst();

        // then
        assertThat(actual, is(notNullValue()));
        assertThat(actual.getId(), is(not(blankOrNullString())));
        assertThat(actual.getLogin(), is(login));
        assertThat(actual.getRequestCount(), is(1L));
    }

    @Test
    public void whenCalledIncrementRequestCount_givenRecordExists_documentUpdatedAndReturned() {
        // given
        String login = "TestLogin";
        Long count = 999L;
        Statistics statistics = Statistics.builder()
                .login(login)
                .requestCount(count)
                .build();

        template.insert(statistics)
                .block();

        // when
        Statistics actual = statisticsRepository.incrementRequestCount(login)
                .block();

        // then
        assertThat(actual, is(notNullValue()));
        assertThat(actual.getId(), is(not(blankOrNullString())));
        assertThat(actual.getLogin(), is(login));
        assertThat(actual.getRequestCount(), is(1000L));
    }

    @Test
    public void whenCalledIncrementRequestCount_givenRecordExists_documentUpdatedAndFetched() {
        // given
        String login = "TestLogin";
        Long count = 999L;
        Statistics statistics = Statistics.builder()
                .login(login)
                .requestCount(count)
                .build();

        template.insert(statistics)
                .block();

        // when
        statisticsRepository.incrementRequestCount(login)
                .block();

        Statistics actual = template.find(new Query(Criteria.where("LOGIN")
                .is(login)), Statistics.class)
                .blockFirst();

        // then
        assertThat(actual, is(notNullValue()));
        assertThat(actual.getId(), is(not(blankOrNullString())));
        assertThat(actual.getLogin(), is(login));
        assertThat(actual.getRequestCount(), is(1000L));
    }

    @Test
    public void whenCalledIncrementRequestCount_givenMultipleCalls_documentUpdatedAndReturned() {
        // given
        String login = "TestLogin";

        // when
        Statistics actual = statisticsRepository.incrementRequestCount(login)
                .then(statisticsRepository.incrementRequestCount(login))
                .then(statisticsRepository.incrementRequestCount(login))
                .then(statisticsRepository.incrementRequestCount(login))
                .then(statisticsRepository.incrementRequestCount(login))
                .block();

        // then
        assertThat(actual, is(notNullValue()));
        assertThat(actual.getId(), is(not(blankOrNullString())));
        assertThat(actual.getLogin(), is(login));
        assertThat(actual.getRequestCount(), is(5L));
    }
}
