package com.pksieminski.empiktest.userservice.adapter.data;

import com.pksieminski.empiktest.userservice.adapter.data.entity.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class StatisticsRepository {

    ReactiveMongoTemplate template;

    @Autowired
    public StatisticsRepository(ReactiveMongoTemplate template) {
        this.template = template;
    }

    public Mono<Statistics> incrementRequestCount(String login) {
        return template.update(Statistics.class)
                .matching(new Query(Criteria.where("LOGIN")
                        .is(login)))
                .apply(new Update().inc("REQUEST_COUNT", 1))
                .withOptions(FindAndModifyOptions.options()
                        .upsert(true)
                        .returnNew(true))
                .findAndModify();
    }
}
