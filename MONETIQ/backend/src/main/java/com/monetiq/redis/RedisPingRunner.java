package com.monetiq.redis;


import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisPingRunner implements CommandLineRunner {

    private final StringRedisTemplate redisTemplate;

    public RedisPingRunner(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void run(String... args) {
        redisTemplate.opsForValue().set("monetiq:test", "OK");
        String value = redisTemplate.opsForValue().get("monetiq:test");
        System.out.println("✅ Redis value = " + value);
    }
}
