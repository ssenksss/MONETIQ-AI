package com.monetiq.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, length = 50)
    private String platform;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "analysis_date")
    private LocalDateTime analysisDate;

    @ElementCollection
    @CollectionTable(name = "profile_suggestions", joinColumns = @JoinColumn(name = "profile_id"))
    @Column(name = "suggestion")
    private List<String> suggestions;

    public Profile() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public LocalDateTime getAnalysisDate() { return analysisDate; }
    public void setAnalysisDate(LocalDateTime analysisDate) { this.analysisDate = analysisDate; }

    public List<String> getSuggestions() { return suggestions; }
    public void setSuggestions(List<String> suggestions) { this.suggestions = suggestions; }
}
