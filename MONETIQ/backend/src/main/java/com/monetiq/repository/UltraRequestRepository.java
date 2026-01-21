package com.monetiq.repository;

import com.monetiq.model.UltraRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UltraRequestRepository extends JpaRepository<UltraRequest, Long> {

}
