package com.monetiq.service;

import com.monetiq.model.Profile;
import com.monetiq.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }
    public Profile createProfile(String username, String platform, Long userId) {
        String u = username == null ? "" : username.trim();
        String p = platform == null ? "" : platform.trim();

        if (u.isEmpty()) throw new IllegalArgumentException("username is required");
        if (p.isEmpty()) throw new IllegalArgumentException("platform is required");
        if (userId == null) throw new IllegalArgumentException("userId is required");

        if (profileRepository.existsByUsername(u)) {
            throw new IllegalArgumentException("Profile already exists for username: " + u);
        }

        Profile profile = new Profile();
        profile.setUsername(u);
        profile.setPlatform(p);
        profile.setUserId(userId);
        profile.setAnalysisDate(null);
        profile.setSuggestions(List.of());

        return profileRepository.save(profile);
    }


    public List<Profile> listProfiles() {
        return profileRepository.findAll();
    }

    public Profile getByUsername(String username) {
        String u = username == null ? "" : username.trim();
        return profileRepository.findByUsername(u).orElse(null);
    }
    public Profile analyzePublicProfile(String username) {
        String u = username == null ? "" : username.trim();
        if (u.isEmpty()) throw new IllegalArgumentException("username is required");

        Profile profile = profileRepository.findByUsername(u)
                .orElseThrow(() -> new IllegalArgumentException("Profile does not exist for username: " + u));

        List<String> suggestions = List.of(
                "Increase posting frequency to 3 posts per week.",
                "Use 5-10 trending hashtags relevant to your niche.",
                "Engage with your followers by responding to comments daily."
        );

        profile.setAnalysisDate(LocalDateTime.now());
        profile.setSuggestions(suggestions);

        return profileRepository.save(profile);
    }

}
