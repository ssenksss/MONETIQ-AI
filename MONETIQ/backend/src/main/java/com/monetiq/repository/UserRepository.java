package com.monetiq.repository;

import com.monetiq.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
@Repository
public class UserRepository {

    private final Map<String, User> users = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(users.get(email));
    }

    public User save(String email, String password, String role) {
        User user = new User(
                idGenerator.getAndIncrement(),
                email,
                password,
                role
        );
        users.put(email, user);
        return user;
    }
}


