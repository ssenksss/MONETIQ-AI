package com.monetiq.model.dto;

import java.util.List;

public class PremiumDayDTO {
    private int day;
    private String title;
    private String contentType;
    private List<String> tasks;

    public PremiumDayDTO() {}

    public PremiumDayDTO(int day, String title, String contentType, List<String> tasks) {
        this.day = day;
        this.title = title;
        this.contentType = contentType;
        this.tasks = tasks;
    }

    public int getDay() { return day; }
    public String getTitle() { return title; }
    public String getContentType() { return contentType; }
    public List<String> getTasks() { return tasks; }
}
