package com.monetiq.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monetiq.model.Plan;
import com.monetiq.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PremiumService {

    private final PlanRepository planRepository;
    private final ObjectMapper objectMapper;

    public PremiumService(PlanRepository planRepository) {
        this.planRepository = planRepository;
        this.objectMapper = new ObjectMapper();
    }


    public String generate30DayPlan(String username) {

        List<Map<String, String>> planDays = new ArrayList<>();

        for (int i = 1; i <= 30; i++) {
            Map<String, String> day = new HashMap<>();
            day.put("day", "Day " + i);
            day.put("task", generateTaskForDay(i));
            planDays.add(day);
        }

        Map<String, Object> planMap = new HashMap<>();
        planMap.put("username", username);
        planMap.put("generatedDate", LocalDateTime.now().toString());
        planMap.put("plan", planDays);

        try {
            String jsonPlan = objectMapper.writeValueAsString(planMap);


            Plan planEntity = new Plan();
            planEntity.setUsername(username);
            planEntity.setGeneratedDate(LocalDateTime.now());
            planEntity.setPlanJson(jsonPlan);

            planRepository.save(planEntity);

            return jsonPlan;

        } catch (Exception e) {
            throw new RuntimeException("Error generating plan JSON", e);
        }
    }


    private String generateTaskForDay(int day) {
        String[] tasks = new String[] {
                "Post a carousel on your main topic",
                "Engage with 10 followers in comments",
                "Use trending hashtags in a post",
                "Create a story poll",
                "Analyze last week's engagement stats",
                "Collaborate with another micro creator",
                "Plan content for next week"
        };
        return tasks[day % tasks.length];
    }
}
