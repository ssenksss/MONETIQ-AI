package com.monetiq.api;

import com.monetiq.model.dto.ApiResponseDTO;
import com.monetiq.model.dto.UltraRequestDTO;
import com.monetiq.service.UltraPremiumService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ultra")
@CrossOrigin(origins ={ "http://localhost:5173", "http://localhost:4200"})
public class UltraController {

    private final UltraPremiumService ultraPremiumService;

    public UltraController(UltraPremiumService ultraPremiumService) {
        this.ultraPremiumService = ultraPremiumService;
    }

    @PostMapping("/request")
    public ResponseEntity<ApiResponseDTO<Map<String, String>>> requestUltra(
            @RequestBody UltraRequestDTO body
    ) {
        if (body == null) {
            return ResponseEntity.badRequest()
                    .body(ApiResponseDTO.error("Body is required."));
        }

        String username = body.getUsername() == null ? "" : body.getUsername().trim();
        String description = body.getDescription() == null ? "" : body.getDescription().trim();

        if (username.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponseDTO.error("Username is required."));
        }

        if (description.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponseDTO.error("Description is required."));
        }

        String msg = ultraPremiumService.submitUltraRequest(username, description);

        return ResponseEntity.ok(
                ApiResponseDTO.success(Map.of("message", msg))
        );
    }
}
