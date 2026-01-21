package com.monetiq.controller;
import com.monetiq.model.dto.ApiResponseDTO;

import com.monetiq.service.PremiumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/premium")
public class PremiumController {

    private final PremiumService premiumService;

    public PremiumController(PremiumService premiumService) {
        this.premiumService = premiumService;
    }

    @GetMapping("/generate-plan")
    public ResponseEntity<ApiResponseDTO<String>> generatePlan(@RequestParam String username) {
        try {
            String planJson = premiumService.generate30DayPlan(username);
            return ResponseEntity.ok(ApiResponseDTO.success(planJson));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error(e.getMessage()));
        }
    }
}
