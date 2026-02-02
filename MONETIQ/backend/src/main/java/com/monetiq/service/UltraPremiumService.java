package com.monetiq.service;

import com.monetiq.model.UltraRequest;
import com.monetiq.repository.UltraRequestRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UltraPremiumService {

    private final UltraRequestRepository ultraRequestRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public UltraPremiumService(
            UltraRequestRepository ultraRequestRepository,
            RedisTemplate<String, String> redisTemplate
    ) {
        this.ultraRequestRepository = ultraRequestRepository;
        this.redisTemplate = redisTemplate;
    }

    public String submitUltraRequest(String username, String description) {
        UltraRequest request = new UltraRequest();
        request.setUsername(username);
        request.setDescription(description);
        request.setRequestDate(LocalDateTime.now());
        request.setStatus("pending");

        ultraRequestRepository.save(request);

        try {
            redisTemplate
                    .opsForList()
                    .rightPush("ultra_requests", username + ": " + description);
        } catch (Exception e) {
            System.out.println("⚠️ Redis push failed: " + e.getMessage());
        }

        return "Our team will review your request and contact you.";
    }
}
