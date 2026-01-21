package com.monetiq.model.dto;

public class AuthResponseDTO {

    private String status;
    private String role;

    public AuthResponseDTO(String status, String role) {
        this.status = status;
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public String getRole() {
        return role;
    }
}
