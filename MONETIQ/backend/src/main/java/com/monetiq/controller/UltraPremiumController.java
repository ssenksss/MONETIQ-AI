package com.monetiq.controller;
import com.monetiq.model.dto.ApiResponseDTO;

import com.monetiq.service.UltraPremiumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ultra/premium")
public class UltraPremiumController {

    private final UltraPremiumService ultraPremiumService;

    public UltraPremiumController(UltraPremiumService ultraPremiumService) {
        this.ultraPremiumService = ultraPremiumService;
    }

    @PostMapping("/request")
    public ResponseEntity<ApiResponseDTO<Map<String, String>>> submitRequest(@RequestBody Map<String, String> payload) {
        try {
            String username = payload.get("username");
            String description = payload.get("description");

            String message = ultraPremiumService.submitUltraRequest(username, description);

            return ResponseEntity.ok(ApiResponseDTO.success(Map.of("message", message)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error(e.getMessage()));
        }
    }
}
