package com.monetiq.api;

import com.monetiq.model.User;
import com.monetiq.model.dto.ApiResponseDTO;
import com.monetiq.model.dto.MeResponseDTO;
import com.monetiq.repository.UserRepository;
import com.monetiq.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:4200"})
public class MeController {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public MeController(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @GetMapping("/api/me")
    public ResponseEntity<ApiResponseDTO<MeResponseDTO>> getMe(
            @RequestHeader(value = "Authorization", required = false) String authHeader
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(ApiResponseDTO.error("Missing or invalid Authorization header"));
        }

        String token = authHeader.substring(7);

        String email = jwtUtil.extractEmail(token);
        if (email == null || email.isBlank()) {
            return ResponseEntity.status(401).body(ApiResponseDTO.error("Invalid token"));
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        MeResponseDTO.Profile profile = new MeResponseDTO.Profile(null, null);
        MeResponseDTO.Plan plan = new MeResponseDTO.Plan(user.getTier(), Arrays.asList());

        MeResponseDTO me = new MeResponseDTO(
                null,
                user.getTier() + "_USER",
                profile,
                plan
        );

        return ResponseEntity.ok(ApiResponseDTO.success(me));
    }
}
