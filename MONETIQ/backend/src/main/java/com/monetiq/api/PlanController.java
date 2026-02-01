package com.monetiq.api;

import com.monetiq.model.dto.ApiResponseDTO;
import com.monetiq.model.dto.PlanResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/plans")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:4200"})
public class PlanController {

    @GetMapping("/free")
    public ResponseEntity<ApiResponseDTO<PlanResponseDTO>> freePlan() {
        return ResponseEntity.ok(ApiResponseDTO.success(
                new PlanResponseDTO("FREE", "Basic monetization tips with limited insights.")
        ));
    }

    @GetMapping("/premium")
    public ResponseEntity<ApiResponseDTO<Map<String, Object>>> premiumPlan() {
        Map<String, Object> data = Map.of(
                "tier", "PREMIUM",
                "items", List.of(
                        Map.of("day", 1, "text", "Optimize bio for conversion"),
                        Map.of("day", 2, "text", "Analyze competitors"),
                        Map.of("day", 3, "text", "Post first Reel")
                )
        );
        return ResponseEntity.ok(ApiResponseDTO.success(data));
    }


}

