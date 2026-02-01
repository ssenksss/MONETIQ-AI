package com.monetiq.api;

import com.monetiq.model.dto.ApiResponseDTO;
import com.monetiq.model.dto.PremiumPlanResponseDTO;
import com.monetiq.service.PremiumPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/premium")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:4200"})
public class PremiumPlanController {

    private final PremiumPlanService premiumPlanService;

    public PremiumPlanController(PremiumPlanService premiumPlanService) {
        this.premiumPlanService = premiumPlanService;
    }

    @GetMapping("/plan")
    public ResponseEntity<ApiResponseDTO<PremiumPlanResponseDTO>> plan(@RequestParam String username) {
        PremiumPlanResponseDTO dto = premiumPlanService.generate(username);
        return ResponseEntity.ok(ApiResponseDTO.success(dto));
    }
}
