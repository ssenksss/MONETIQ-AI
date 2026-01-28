package com.monetiq.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;

@Document(collection = "creator_analysis")
public class CreatorAnalysisDoc {

    @Id
    private String id;

    private Long profileId;

    public Long getProfileId() { return profileId; }
    public void setProfileId(Long profileId) { this.profileId = profileId; }


    private String username;
    private String platform;
    private String tier;

    private Map<String, Object> profile;
    private Map<String, Object> analysis;
    private Map<String, Object> monetization;

    private Metadata metadata;

    public static class Metadata {
        private String generatedBy;
        private Instant createdAt;
        private String version;

        public String getGeneratedBy() { return generatedBy; }
        public void setGeneratedBy(String generatedBy) { this.generatedBy = generatedBy; }
        public Instant getCreatedAt() { return createdAt; }
        public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
        public String getVersion() { return version; }
        public void setVersion(String version) { this.version = version; }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }

    public String getTier() { return tier; }
    public void setTier(String tier) { this.tier = tier; }

    public Map<String, Object> getProfile() { return profile; }
    public void setProfile(Map<String, Object> profile) { this.profile = profile; }

    public Map<String, Object> getAnalysis() { return analysis; }
    public void setAnalysis(Map<String, Object> analysis) { this.analysis = analysis; }

    public Map<String, Object> getMonetization() { return monetization; }
    public void setMonetization(Map<String, Object> monetization) { this.monetization = monetization; }

    public Metadata getMetadata() { return metadata; }
    public void setMetadata(Metadata metadata) { this.metadata = metadata; }
}
