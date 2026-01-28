package com.monetiq.api;

import com.monetiq.model.User;
import com.monetiq.model.dto.ApiResponseDTO;
import com.monetiq.model.dto.AuthResponseDTO;
import com.monetiq.model.dto.LoginRequestDTO;
import com.monetiq.model.dto.SignupRequestDTO;
import com.monetiq.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDTO<AuthResponseDTO>> signup(@RequestBody(required = false) SignupRequestDTO req) {
        try {
            if (req == null) return ResponseEntity.badRequest().body(ApiResponseDTO.error("body is required"));

            User user = authService.signup(req.getEmail(), req.getPassword());

            AuthResponseDTO resp = new AuthResponseDTO("OK", user.getTier());
            resp.setUserId(user.getId());
            return ResponseEntity.ok(ApiResponseDTO.success(resp));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponseDTO.error(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<AuthResponseDTO>> login(
            @RequestBody(required = false) LoginRequestDTO req
    ) {
        try {
            if (req == null) {
                return ResponseEntity.badRequest()
                        .body(ApiResponseDTO.error("body is required"));
            }

            User user = authService.login(req.getEmail(), req.getPassword());

            AuthResponseDTO resp = new AuthResponseDTO("OK", user.getTier());
            resp.setUserId(user.getId());

            return ResponseEntity.ok(ApiResponseDTO.success(resp));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponseDTO.error(e.getMessage()));
        } catch (Exception e) {
            String msg = (e.getMessage() == null || e.getMessage().isBlank())
                    ? "Internal error"
                    : e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.error(msg));
        }
    }

}
