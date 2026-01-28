package com.monetiq.api;

import com.monetiq.model.Profile;
import com.monetiq.model.dto.ApiResponseDTO;
import com.monetiq.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/profiles")
@CrossOrigin(origins = "http://localhost:5173")
public class ProfileApiController {

    private final ProfileService profileService;

    public ProfileApiController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<Profile>> create(@RequestBody(required = false) Map<String, String> body) {
        try {
            if (body == null) return ResponseEntity.badRequest().body(ApiResponseDTO.error("body is required"));

            String username = body.get("username");
            String platform = body.get("platform");
            String userIdRaw = body.get("userId");

            Long userId = (userIdRaw == null || userIdRaw.trim().isEmpty())
                    ? null
                    : Long.valueOf(userIdRaw.trim());

            Profile saved = profileService.createProfile(username, platform, userId);
            return ResponseEntity.ok(ApiResponseDTO.success(saved));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponseDTO.error(e.getMessage()));
        }
    }


    // LIST
    @GetMapping
    public ResponseEntity<ApiResponseDTO<Object>> list() {
        try {
            return ResponseEntity.ok(ApiResponseDTO.success(profileService.listProfiles()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponseDTO.error(e.getMessage()));
        }
    }

    // GET by username
    @GetMapping("/{username}")
    public ResponseEntity<ApiResponseDTO<Profile>> get(@PathVariable String username) {
        try {
            Profile p = profileService.getByUsername(username);
            if (p == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDTO.error("Profile not found: " + username));
            }
            return ResponseEntity.ok(ApiResponseDTO.success(p));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponseDTO.error(e.getMessage()));
        }
    }
}
