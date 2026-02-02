package com.monetiq.model.dto;

public class PremiumItemDTO {
    private int day;
    private String text;

    public PremiumItemDTO() {}

    public PremiumItemDTO(int day, String text) {
        this.day = day;
        this.text = text;
    }

    public int getDay() { return day; }
    public String getText() { return text; }
}
