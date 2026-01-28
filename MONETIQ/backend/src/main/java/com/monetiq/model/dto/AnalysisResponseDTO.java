package com.monetiq.model.dto;

import java.time.Instant;

public class AnalysisResponseDTO {

    private String username;
    private String plan;
    private String tier;
    private Instant createdAt;

    public AnalysisResponseDTO() {}

    public AnalysisResponseDTO(String username, String plan, String tier) {
        this.username = username;
        this.plan = plan;
        this.tier = tier;
        this.createdAt = Instant.now();
    }

    public String getUsername() { return username; }
    public String getPlan() { return plan; }
    public String getTier() { return tier; }
    public Instant getCreatedAt() { return createdAt; }
}
