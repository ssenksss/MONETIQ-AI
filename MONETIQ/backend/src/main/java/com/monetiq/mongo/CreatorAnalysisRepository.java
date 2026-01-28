package com.monetiq.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CreatorAnalysisRepository extends MongoRepository<CreatorAnalysisDoc, String> {
    Optional<CreatorAnalysisDoc> findByUsername(String username);
}
