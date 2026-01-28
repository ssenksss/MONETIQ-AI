package com.monetiq.controller;

import com.monetiq.model.Profile;
import com.monetiq.model.dto.ApiResponseDTO;
import com.monetiq.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "http://localhost:5173")
public class ProfileController {

    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/analyze")
    public ResponseEntity<ApiResponseDTO<Profile>> analyzeProfile(@RequestParam String username) {
        try {
            Profile analysis = profileService.analyzePublicProfile(username);
            return ResponseEntity.ok(ApiResponseDTO.success(analysis));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.error(e.getMessage()));

        } catch (Exception e) {
            log.error("Profile analyze failed for username={}", username, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error("Internal error"));
        }
    }
}
