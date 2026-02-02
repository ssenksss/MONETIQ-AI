package com.monetiq.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class AnalysisCacheService {

    private final StringRedisTemplate redis;
    private final ObjectMapper om;

    public AnalysisCacheService(StringRedisTemplate redis, ObjectMapper om) {
        this.redis = redis;
        this.om = om;
    }

    private String key(String username) {
        return "analysis:" + username.toLowerCase().trim();
    }

    public <T> T get(String username, Class<T> clazz) {
        try {
            String json = redis.opsForValue().get(key(username));
            if (json == null) return null;
            return om.readValue(json, clazz);
        } catch (Exception e) {
            System.out.println("REDIS GET FAILED -> " + key(username));
            e.printStackTrace();
            return null;
        }
    }

    public void put(String username, Object value, Duration ttl) {
        try {
            String json = om.writeValueAsString(value);
            redis.opsForValue().set(key(username), json, ttl);
            System.out.println("REDIS SET OK -> " + key(username));
        } catch (Exception e) {
            System.out.println("REDIS SET FAILED -> " + key(username));
            e.printStackTrace();
        }
    }

}
