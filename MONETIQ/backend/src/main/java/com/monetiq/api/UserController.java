package com.monetiq.api;

import com.monetiq.model.User;
import com.monetiq.model.dto.ApiResponseDTO;
import com.monetiq.repository.UserRepository;
import com.monetiq.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:4200"})
public class UserController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserController(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/upgrade/premium")
    public ResponseEntity<ApiResponseDTO<String>> upgradeToPremium(
            @RequestHeader("Authorization") String authHeader
    ) {
        if (!authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(ApiResponseDTO.error("Invalid token"));
        }

        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setTier("PREMIUM");
        userRepository.save(user);

        return ResponseEntity.ok(ApiResponseDTO.success("Upgraded to PREMIUM"));
    }
    @PostMapping("/upgrade/ultra")
    public ResponseEntity<ApiResponseDTO<String>> upgradeToUltra(
            @RequestHeader("Authorization") String authHeader
    ) {
        if (!authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(ApiResponseDTO.error("Invalid token"));
        }

        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setTier("ULTRA");
        userRepository.save(user);

        return ResponseEntity.ok(ApiResponseDTO.success("Upgraded to ULTRA"));
    }

}
