package com.monetiq.repository;

import com.monetiq.model.AnalysisRecord;
import com.monetiq.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnalysisRecordRepository extends JpaRepository<AnalysisRecord, Long> {
    Optional<AnalysisRecord> findFirstByUserOrderByCreatedAtDesc(User user);
    List<AnalysisRecord> findByUserOrderByCreatedAtDesc(User user);
}
