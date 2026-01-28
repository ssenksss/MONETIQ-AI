package com.monetiq.service;

import com.monetiq.model.User;
import com.monetiq.repository.UserRepository;
import org.springframework.stereotype.Service;
@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signup(String email, String password) {
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("email is required");
        if (password == null || password.isBlank())
            throw new IllegalArgumentException("password is required");

        if (userRepository.findByEmail(email).isPresent())
            throw new IllegalArgumentException("User already exists");

        User user = new User(email, password, "FREE");
        return userRepository.save(user);
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!user.getPassword().equals(password))
            throw new IllegalArgumentException("Invalid password");

        return user;
    }
}
