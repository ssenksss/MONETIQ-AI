package com.monetiq.model.dto;

public class ProfileCreateRequestDTO {
    private String username;
    private String platform;

    public ProfileCreateRequestDTO() {}

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }
}
