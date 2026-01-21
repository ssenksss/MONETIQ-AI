package com.monetiq.service;

import com.monetiq.model.Profile;
import com.monetiq.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Map<String, Object> analyzePublicProfile(String username) {

        List<String> suggestions = new ArrayList<>();
        suggestions.add("Increase posting frequency to 3 posts per week.");
        suggestions.add("Use 5-10 trending hashtags relevant to your niche.");
        suggestions.add("Engage with your followers by responding to comments daily.");


        Profile profile = new Profile();
        profile.setUsername(username);
        profile.setAnalysisDate(LocalDateTime.now());
        profile.setSuggestions(suggestions);
        profileRepository.save(profile);

        Map<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("analysisDate", profile.getAnalysisDate().toString());
        result.put("suggestions", suggestions);

        return result;
    }
}
