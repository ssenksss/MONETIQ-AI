package com.monetiq.api;

import com.monetiq.model.dto.ApiResponseDTO;
import com.monetiq.model.dto.MeResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class MeController {

    @GetMapping("/api/me")
    public ResponseEntity<ApiResponseDTO<MeResponseDTO>> getMe() {
        MeResponseDTO.Profile profile = new MeResponseDTO.Profile(null, null);
        MeResponseDTO.Plan plan = new MeResponseDTO.Plan("FREE", Arrays.asList());

        MeResponseDTO me = new MeResponseDTO("teodora.garic", "FREE_USER", profile, plan);
        return ResponseEntity.ok(ApiResponseDTO.success(me));
    }


}
