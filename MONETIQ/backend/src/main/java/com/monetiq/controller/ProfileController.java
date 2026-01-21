package com.monetiq.controller;

import com.monetiq.model.dto.ApiResponseDTO;
import com.monetiq.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "http://localhost:5173")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/analyze")
    public ResponseEntity<ApiResponseDTO<Map<String, Object>>> analyzeProfile(@RequestParam String username) {
        try {
            Map<String, Object> analysis = profileService.analyzePublicProfile(username);
            return ResponseEntity.ok(ApiResponseDTO.success(analysis));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error(e.getMessage()));
        }
    }

}
