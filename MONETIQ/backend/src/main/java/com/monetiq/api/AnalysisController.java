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
    public ResponseEntity<ApiResponseDTO<AnalysisResponseDTO>> analyze(@RequestBody AnalysisRequestDTO request) {
        try {
            String plan = analysisService.generateAlphaPlan(request.getUsername());
            AnalysisResponseDTO response = new AnalysisResponseDTO(request.getUsername(), plan, "FREE");
            return ResponseEntity.ok(ApiResponseDTO.success(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error(e.getMessage()));
        }
    }

}
