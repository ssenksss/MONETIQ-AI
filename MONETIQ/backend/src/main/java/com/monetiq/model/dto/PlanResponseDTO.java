package com.monetiq.model.dto;

public class PlanResponseDTO {

    private String tier;
    private String content;

    public PlanResponseDTO(String tier, String content) {
        this.tier = tier;
        this.content = content;
    }

    public String getTier() {
        return tier;
    }

    public String getContent() {
        return content;
    }
}
