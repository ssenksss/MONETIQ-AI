package com.monetiq.api;

import com.monetiq.model.dto.AnalysisRequestDTO;
import com.monetiq.model.dto.AnalysisResponseDTO;
import com.monetiq.model.dto.ApiResponseDTO;
import com.monetiq.service.AnalysisService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analysis")
@CrossOrigin(origins = "http://localhost:5173")
public class AnalysisController {

    private final AnalysisService analysisService;

    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }
    @PostMapping
    public ResponseEntity<ApiResponseDTO<AnalysisResponseDTO>> analyze(
            @RequestBody(required = false) AnalysisRequestDTO request
    ) {
        try {
            if (request == null || request.getUsername() == null || request.getUsername().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(ApiResponseDTO.error("username is required"));
            }

            AnalysisResponseDTO response = analysisService.getOrGenerate(request.getUsername());
            return ResponseEntity.ok(ApiResponseDTO.success(response));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponseDTO.error(e.getMessage()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error("Internal server error"));
        }
    }



    @GetMapping("/{username}")
    public ResponseEntity<ApiResponseDTO<AnalysisResponseDTO>> get(@PathVariable String username) {
        try {
            AnalysisResponseDTO existing = analysisService.getExisting(username);
            if (existing == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDTO.error("No analysis found for username: " + username));
            }
            return ResponseEntity.ok(ApiResponseDTO.success(existing));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error(e.getMessage()));
        }
    }

}
