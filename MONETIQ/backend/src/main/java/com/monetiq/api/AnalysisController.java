package com.monetiq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monetiq.model.AnalysisRecord;
import com.monetiq.model.User;
import com.monetiq.model.dto.AnalysisRequestDTO;
import com.monetiq.model.dto.AnalysisResponseDTO;
import com.monetiq.model.dto.ApiResponseDTO;
import com.monetiq.repository.AnalysisRecordRepository;
import com.monetiq.repository.UserRepository;
import com.monetiq.security.JwtUtil;
import com.monetiq.service.AnalysisService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analysis")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:4200"})
public class AnalysisController {

    private final AnalysisService analysisService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AnalysisRecordRepository analysisRecordRepository;
    private final ObjectMapper objectMapper;

    public AnalysisController(
            AnalysisService analysisService,
            JwtUtil jwtUtil,
            UserRepository userRepository,
            AnalysisRecordRepository analysisRecordRepository,
            ObjectMapper objectMapper
    ) {
        this.analysisService = analysisService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.analysisRecordRepository = analysisRecordRepository;
        this.objectMapper = objectMapper;
    }

    private User requireUser(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Missing token");
        }
        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<AnalysisResponseDTO>> analyze(
            @RequestHeader(value = "Authorization", required = false) String auth,
            @RequestBody(required = false) AnalysisRequestDTO request
    ) {
        try {
            if (request == null || request.getUsername() == null || request.getUsername().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(ApiResponseDTO.error("username is required"));
            }

            User user = requireUser(auth);

            AnalysisResponseDTO response = analysisService.getOrGenerate(request.getUsername().trim());

            String json = objectMapper.writeValueAsString(response);
            analysisRecordRepository.save(new AnalysisRecord(user, request.getUsername().trim(), json));

            return ResponseEntity.ok(ApiResponseDTO.success(response));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error("Internal server error"));
        }
    }

    @GetMapping("/latest")
    public ResponseEntity<ApiResponseDTO<AnalysisResponseDTO>> latest(
            @RequestHeader(value = "Authorization", required = false) String auth
    ) {
        try {
            User user = requireUser(auth);

            AnalysisRecord rec = analysisRecordRepository
                    .findFirstByUserOrderByCreatedAtDesc(user)
                    .orElse(null);

            if (rec == null) {
                return ResponseEntity.ok(ApiResponseDTO.success(null));
            }

            AnalysisResponseDTO dto = objectMapper.readValue(rec.getResponseJson(), AnalysisResponseDTO.class);
            return ResponseEntity.ok(ApiResponseDTO.success(dto));

        } catch (Exception e) {
            String msg = (e.getMessage() == null || e.getMessage().isBlank())
                    ? e.getClass().getSimpleName()
                    : e.getClass().getSimpleName() + ": " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error(msg));
        }


    }

    @GetMapping("/history")
    public ResponseEntity<ApiResponseDTO<List<AnalysisResponseDTO>>> history(
            @RequestHeader(value = "Authorization", required = false) String auth
    ) {
        try {
            User user = requireUser(auth);

            List<AnalysisResponseDTO> out = analysisRecordRepository
                    .findByUserOrderByCreatedAtDesc(user)
                    .stream()
                    .map(rec -> {
                        try {
                            return objectMapper.readValue(rec.getResponseJson(), AnalysisResponseDTO.class);
                        } catch (Exception e) {
                            return null;
                        }
                    })
                    .filter(x -> x != null)
                    .toList();

            return ResponseEntity.ok(ApiResponseDTO.success(out));
        } catch (Exception e) {
            String msg = (e.getMessage() == null || e.getMessage().isBlank())
                    ? e.getClass().getSimpleName()
                    : e.getClass().getSimpleName() + ": " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error(msg));
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
