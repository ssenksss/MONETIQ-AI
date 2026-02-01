package com.monetiq.model.dto;

import java.time.Instant;
import java.util.List;

public class PremiumPlanResponseDTO {

    private String username;
    private String tier;
    private String planText;
    private List<PremiumDayDTO> days;
    private List<PremiumItemDTO> items;
    private Instant createdAt;

    public PremiumPlanResponseDTO() {}

    public PremiumPlanResponseDTO(
            String username,
            String tier,
            String planText,
            List<PremiumDayDTO> days,
            List<PremiumItemDTO> items
    ) {
        this.username = username;
        this.tier = tier;
        this.planText = planText;
        this.days = days;
        this.items = items;
        this.createdAt = Instant.now();
    }

    public String getUsername() { return username; }
    public String getTier() { return tier; }
    public String getPlanText() { return planText; }
    public List<PremiumDayDTO> getDays() { return days; }
    public List<PremiumItemDTO> getItems() { return items; }
    public Instant getCreatedAt() { return createdAt; }
}
