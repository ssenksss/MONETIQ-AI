package com.monetiq.model.dto;

import java.time.Instant;
import java.util.List;

public class AnalysisResponseDTO {

    private String username;
    private String plan;
    private String tier;
    private Instant createdAt;
    private List<String> suggestions;

    public AnalysisResponseDTO() {}

    public AnalysisResponseDTO(String username, String plan, String tier) {
        this.username = username;
        this.plan = plan;
        this.tier = tier;
        this.createdAt = Instant.now();
    }

    public AnalysisResponseDTO(String username, String plan, String tier, List<String> suggestions) {
        this.username = username;
        this.plan = plan;
        this.tier = tier;
        this.suggestions = suggestions;
        this.createdAt = Instant.now();
    }

    public String getUsername() { return username; }
    public String getPlan() { return plan; }
    public String getTier() { return tier; }
    public Instant getCreatedAt() { return createdAt; }
    public List<String> getSuggestions() { return suggestions; }

    public void setUsername(String username) { this.username = username; }
    public void setPlan(String plan) { this.plan = plan; }
    public void setTier(String tier) { this.tier = tier; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public void setSuggestions(List<String> suggestions) { this.suggestions = suggestions; }
}
