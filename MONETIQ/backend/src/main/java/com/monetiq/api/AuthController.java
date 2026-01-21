package com.monetiq.api;

import com.monetiq.model.dto.ApiResponseDTO;
import com.monetiq.model.dto.AuthResponseDTO;
import com.monetiq.model.dto.SignupRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<AuthResponseDTO>> login() {
        AuthResponseDTO auth = new AuthResponseDTO("OK", "FREE_USER");
        return ResponseEntity.ok(ApiResponseDTO.success(auth));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDTO<AuthResponseDTO>> signup(@RequestBody SignupRequestDTO request) {
        AuthResponseDTO auth = new AuthResponseDTO("OK", "FREE_USER");
        return ResponseEntity.ok(ApiResponseDTO.success(auth));
    }



}
