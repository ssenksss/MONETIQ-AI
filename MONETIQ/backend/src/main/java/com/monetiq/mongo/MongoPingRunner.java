package com.monetiq.mongo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class MongoPingRunner implements CommandLineRunner {

    private final MongoTemplate mongoTemplate;

    public MongoPingRunner(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void run(String... args) {
        System.out.println("✅ Mongo DB name = " + mongoTemplate.getDb().getName());
        System.out.println("✅ Mongo collections = " + mongoTemplate.getCollectionNames());
    }
}
