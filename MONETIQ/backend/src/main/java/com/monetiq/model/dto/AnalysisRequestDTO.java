package com.monetiq.model.dto;

public class AnalysisRequestDTO {

    private String username;

    public AnalysisRequestDTO() {}

    public AnalysisRequestDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
