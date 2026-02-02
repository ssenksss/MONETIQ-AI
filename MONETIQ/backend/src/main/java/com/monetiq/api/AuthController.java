package com.monetiq.api;

import com.monetiq.model.User;
import com.monetiq.model.dto.ApiResponseDTO;
import com.monetiq.model.dto.AuthResponseDTO;
import com.monetiq.model.dto.LoginRequestDTO;
import com.monetiq.model.dto.SignupRequestDTO;
import com.monetiq.security.JwtUtil;
import com.monetiq.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins ={ "http://localhost:5173", "http://localhost:4200"})
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }
    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDTO<AuthResponseDTO>> signup(@RequestBody(required = false) SignupRequestDTO req) {
        try {
            if (req == null) return ResponseEntity.badRequest().body(ApiResponseDTO.error("body is required"));
            if (req.getEmail() == null || req.getEmail().isBlank()) return ResponseEntity.badRequest().body(ApiResponseDTO.error("email is required"));
            if (req.getPassword() == null || req.getPassword().isBlank()) return ResponseEntity.badRequest().body(ApiResponseDTO.error("password is required"));

            User user = authService.signup(req.getEmail(), req.getPassword());

            String tier = user.getTier();
            if (tier == null || tier.isBlank()) tier = "FREE";

            String token = jwtUtil.generateToken(user.getEmail(), tier);

            AuthResponseDTO resp = new AuthResponseDTO("OK", tier);
            resp.setUserId(user.getId());
            resp.setToken(token);

            return ResponseEntity.ok(ApiResponseDTO.success(resp));

        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error("Email already exists."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error(e.getMessage()));
        } catch (Exception e) {
            String msg = (e.getMessage() == null || e.getMessage().isBlank()) ? e.getClass().getSimpleName() : e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponseDTO.error(msg));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<AuthResponseDTO>> login(@RequestBody(required = false) LoginRequestDTO req) {
        try {
            if (req == null) return ResponseEntity.badRequest().body(ApiResponseDTO.error("body is required"));
            if (req.getEmail() == null || req.getEmail().isBlank()) return ResponseEntity.badRequest().body(ApiResponseDTO.error("email is required"));
            if (req.getPassword() == null || req.getPassword().isBlank()) return ResponseEntity.badRequest().body(ApiResponseDTO.error("password is required"));

            User user = authService.login(req.getEmail(), req.getPassword());

            String tier = user.getTier();
            if (tier == null || tier.isBlank()) tier = "FREE";

            String token = jwtUtil.generateToken(user.getEmail(), tier);

            AuthResponseDTO resp = new AuthResponseDTO("OK", tier);
            resp.setUserId(user.getId());
            resp.setToken(token);

            return ResponseEntity.ok(ApiResponseDTO.success(resp));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error(e.getMessage()));
        } catch (Exception e) {
            String msg = (e.getMessage() == null || e.getMessage().isBlank()) ? e.getClass().getSimpleName() : e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponseDTO.error(msg));
        }
    }

}
