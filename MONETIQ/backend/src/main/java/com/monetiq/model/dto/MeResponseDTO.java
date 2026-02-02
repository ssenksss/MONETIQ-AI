package com.monetiq.model.dto;

import java.util.List;

public class MeResponseDTO {
    private String username;
    private String role;
    private Profile profile;
    private Plan plan;

    public MeResponseDTO(String username, String role, Profile profile, Plan plan) {
        this.username = username;
        this.role = role;
        this.profile = profile;
        this.plan = plan;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Profile getProfile() { return profile; }
    public void setProfile(Profile profile) { this.profile = profile; }

    public Plan getPlan() { return plan; }
    public void setPlan(Plan plan) { this.plan = plan; }


    public static class Profile {
        private String analysisDate;
        private List<Suggestion> suggestions;

        public Profile(String analysisDate, List<Suggestion> suggestions) {
            this.analysisDate = analysisDate;
            this.suggestions = suggestions;
        }

        public String getAnalysisDate() { return analysisDate; }
        public void setAnalysisDate(String analysisDate) { this.analysisDate = analysisDate; }

        public List<Suggestion> getSuggestions() { return suggestions; }
        public void setSuggestions(List<Suggestion> suggestions) { this.suggestions = suggestions; }

        public static class Suggestion {
            private String text;

            public Suggestion(String text) { this.text = text; }
            public String getText() { return text; }
            public void setText(String text) { this.text = text; }
        }
    }


    public static class Plan {
        private String type;
        private List<String> items;

        public Plan(String type, List<String> items) {
            this.type = type;
            this.items = items;
        }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public List<String> getItems() { return items; }
        public void setItems(List<String> items) { this.items = items; }
    }
}
