package com.monetiq.model.dto;

import java.time.LocalDateTime;

public class ProfileResponseDTO {
    private Long id;
    private String username;
    private String platform;
    private LocalDateTime analysisDate;

    public ProfileResponseDTO() {}

    public ProfileResponseDTO(Long id, String username, String platform, LocalDateTime analysisDate) {
        this.id = id;
        this.username = username;
        this.platform = platform;
        this.analysisDate = analysisDate;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPlatform() { return platform; }
    public LocalDateTime getAnalysisDate() { return analysisDate; }
}
