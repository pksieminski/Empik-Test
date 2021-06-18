package com.pksieminski.empiktest.userservice.adapter.data;

import com.pksieminski.empiktest.userservice.adapter.data.model.GitHubUserResponse;
import com.pksieminski.empiktest.userservice.domain.model.User;
import com.pksieminski.empiktest.userservice.domain.port.outbound.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class UserRepositoryProxy implements UserRepository {

    private final StatisticsRepository statisticsRepository;
    private final GitHubApiService gitHubApiService;
    private final CalculationService calculationService;

    @Autowired
    public UserRepositoryProxy(StatisticsRepository statisticsRepository, GitHubApiService gitHubApiService, CalculationService calculationService) {
        this.statisticsRepository = statisticsRepository;
        this.gitHubApiService = gitHubApiService;
        this.calculationService = calculationService;
    }

    @Override
    public Mono<User> findByLogin(String login) {
        // Execute requests in sequence whenever previous ends
        // For alternative solution, request count increment can be called as parallel operation with subscribe

        return statisticsRepository.incrementRequestCount(login)
                .then(gitHubApiService.getUserDetails(login))
                .map(this::assembleUser);
    }

    private User assembleUser(GitHubUserResponse user) {
        return User.builder()
                .id(user.getId())
                .login(user.getLogin())
                .name(user.getName())
                .type(user.getType())
                .avatarUrl(user.getAvatarUrl())
                .createdAt(user.getCreatedAt())
                .calculations(calculationService.calculate(user.getFollowers(), user.getPublicRepos())
                        .getCalculation())
                .build();
    }
}
