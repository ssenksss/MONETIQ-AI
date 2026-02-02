package com.monetiq.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "analysis")
public class Analysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="instagram_username", nullable=false)
    private String instagramUsername;

    @Lob
    @Column(name="result_json", nullable=false, columnDefinition = "TEXT")
    private String resultJson;

    @Column(name="created_at", nullable=false)
    private LocalDateTime createdAt;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    public Analysis() {}

    public Analysis(User user, String instagramUsername, String resultJson) {
        this.user = user;
        this.instagramUsername = instagramUsername;
        this.resultJson = resultJson;
        this.createdAt = LocalDateTime.now();
    }

}
