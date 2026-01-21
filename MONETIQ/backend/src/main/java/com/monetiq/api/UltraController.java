package com.monetiq.api;

import com.monetiq.model.dto.ApiResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ultra")
@CrossOrigin(origins = "http://localhost:5173")
public class UltraController {

    @PostMapping("/request")
    public ResponseEntity<ApiResponseDTO<Map<String, String>>> requestUltra() {
        return ResponseEntity.ok(ApiResponseDTO.success(Map.of("message", "Ultra Premium request received. Our team will review it.")));
    }
}
