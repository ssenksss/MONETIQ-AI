package com.monetiq.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "analysis_records")
public class AnalysisRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "instagram_username", nullable = false)
    private String instagramUsername;


    @Column(name = "response_json", nullable = false, columnDefinition = "TEXT")
    private String responseJson;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public AnalysisRecord() {}

    public AnalysisRecord(User user, String instagramUsername, String responseJson) {
        this.user = user;
        this.instagramUsername = instagramUsername;
        this.responseJson = responseJson;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public String getInstagramUsername() { return instagramUsername; }
    public String getResponseJson() { return responseJson; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
