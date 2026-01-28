package com.monetiq.model.dto;

public class AuthResponseDTO {

    private String status;
    private String tier;
    private Long userId;

    public AuthResponseDTO() {}

    public AuthResponseDTO(String status, String tier) {
        this.status = status;
        this.tier = tier;
    }

    public String getStatus() { return status; }
    public String getTier() { return tier; }
    public Long getUserId() { return userId; }

    public void setStatus(String status) { this.status = status; }
    public void setTier(String tier) { this.tier = tier; }
    public void setUserId(Long userId) { this.userId = userId; }
}
