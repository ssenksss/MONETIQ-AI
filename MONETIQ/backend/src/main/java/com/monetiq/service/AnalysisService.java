package com.monetiq.service;

import com.monetiq.model.dto.AnalysisResponseDTO;
import com.monetiq.mongo.CreatorAnalysisDoc;
import com.monetiq.mongo.CreatorAnalysisRepository;
import com.monetiq.redis.AnalysisCacheService;
import com.monetiq.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AnalysisService {

    private final CreatorAnalysisRepository mongoRepo;
    private final AnalysisCacheService cache;
    private final ProfileRepository profileRepo;

    public AnalysisService(
            CreatorAnalysisRepository mongoRepo,
            AnalysisCacheService cache,
            ProfileRepository profileRepo
    ) {
        this.mongoRepo = mongoRepo;
        this.cache = cache;
        this.profileRepo = profileRepo;
    }

    public AnalysisResponseDTO getOrGenerate(String username) {
        String u = username == null ? "" : username.trim();

        var profile = profileRepo.findByUsername(u)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Profile does not exist for username: " + u
                ));

        AnalysisResponseDTO cached = cache.get(u, AnalysisResponseDTO.class);
        if (cached != null) {
            profile.setAnalysisDate(LocalDateTime.now());
            profileRepo.save(profile);
            System.out.println("CACHE HIT -> updating analysis_date for " + profile.getUsername());

            return cached;
        }

        CreatorAnalysisDoc existing = mongoRepo.findByUsername(u).orElse(null);
        if (existing != null) {
            AnalysisResponseDTO dto = new AnalysisResponseDTO(
                    existing.getUsername(),
                    extractPlan(existing),
                    existing.getTier() == null ? "FREE" : existing.getTier()
            );

            cache.put(u, dto, Duration.ofMinutes(10));

            profile.setAnalysisDate(LocalDateTime.now());
            System.out.println("MONGO HIT -> updating analysis_date for " + profile.getUsername());

            profileRepo.save(profile);

            return dto;
        }

        String plan = generateAlphaPlan(u);
        AnalysisResponseDTO dto = new AnalysisResponseDTO(u, plan, "FREE");

        CreatorAnalysisDoc doc = new CreatorAnalysisDoc();
        doc.setProfileId(profile.getId());
        doc.setUsername(u);
        doc.setPlatform(profile.getPlatform());
        doc.setTier("FREE");

        Map<String, Object> analysis = new HashMap<>();
        analysis.put("plan", plan);
        doc.setAnalysis(analysis);

        doc.setProfile(Map.of("source", "manual", "data", Map.of()));
        doc.setMonetization(Map.of("level", "alpha", "note", "fake-plan"));

        CreatorAnalysisDoc.Metadata md = new CreatorAnalysisDoc.Metadata();
        md.setGeneratedBy("alpha-generator");
        md.setCreatedAt(Instant.now());
        md.setVersion("v1");
        doc.setMetadata(md);

        mongoRepo.save(doc);
        cache.put(u, dto, Duration.ofMinutes(10));

        profile.setAnalysisDate(LocalDateTime.now());
        System.out.println("GENERATE -> updating analysis_date for " + profile.getUsername());

        profileRepo.save(profile);

        return dto;
    }

    public AnalysisResponseDTO getExisting(String username) {
        String u = username == null ? "" : username.trim();

        var profile = profileRepo.findByUsername(u).orElse(null);

        AnalysisResponseDTO cached = cache.get(u, AnalysisResponseDTO.class);
        if (cached != null) {
            if (profile != null) {
                profile.setAnalysisDate(LocalDateTime.now());
                profileRepo.save(profile);
            }
            return cached;
        }

        CreatorAnalysisDoc existing = mongoRepo.findByUsername(u).orElse(null);
        if (existing == null) return null;

        AnalysisResponseDTO dto = new AnalysisResponseDTO(
                existing.getUsername(),
                extractPlan(existing),
                existing.getTier() == null ? "FREE" : existing.getTier()
        );

        cache.put(u, dto, Duration.ofMinutes(10));

        if (profile != null) {
            profile.setAnalysisDate(LocalDateTime.now());
            profileRepo.save(profile);
        }

        return dto;
    }

    public String generateAlphaPlan(String username) {
        return """
        Monetization Analysis for @%s

        1. Focus on short-form reels with strong hooks
        2. Monetize through brand collaborations
        3. Introduce a digital product within 30 days
        4. Optimize posting schedule for engagement

        This is an internal alpha-generated plan.
        """.formatted(username);
    }

    private String extractPlan(CreatorAnalysisDoc doc) {
        if (doc.getAnalysis() == null) return "";
        Object plan = doc.getAnalysis().get("plan");
        return plan == null ? "" : String.valueOf(plan);
    }
}
