package com.monetiq.model.dto;

public class AnalysisHistoryItemDTO {
    public Long id;
    public String username;
    public String createdAt;
    public AnalysisResponseDTO response;

    public AnalysisHistoryItemDTO(Long id, String username, String createdAt, AnalysisResponseDTO response) {
        this.id = id;
        this.username = username;
        this.createdAt = createdAt;
        this.response = response;
    }
}
