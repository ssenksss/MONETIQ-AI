package com.monetiq.api;

import com.monetiq.model.UltraRequest;
import com.monetiq.repository.UltraRequestRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/admin/ultra")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:4200"})
public class AdminUltraController {

    private final UltraRequestRepository ultraRequestRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public AdminUltraController(UltraRequestRepository ultraRequestRepository,
                                RedisTemplate<String, String> redisTemplate) {
        this.ultraRequestRepository = ultraRequestRepository;
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/requests")
    public List<UltraRequest> getRequests(@RequestParam(required = false) String status,
                                          @RequestParam(required = false) String search) {

        List<UltraRequest> all = ultraRequestRepository.findAll(
                Sort.by(Sort.Direction.DESC, "requestDate")
        );

        Stream<UltraRequest> s = all.stream();

        if (status != null && !status.isBlank()) {
            String st = status.trim().toUpperCase(Locale.ROOT);
            s = s.filter(r -> r.getStatus() != null && r.getStatus().equalsIgnoreCase(st));
        }

        if (search != null && !search.isBlank()) {
            String q = search.trim().toLowerCase(Locale.ROOT);
            s = s.filter(r ->
                    (r.getUsername() != null && r.getUsername().toLowerCase(Locale.ROOT).contains(q)) ||
                            (r.getDescription() != null && r.getDescription().toLowerCase(Locale.ROOT).contains(q))
            );
        }

        return s.toList();
    }


    @PatchMapping("/requests/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        UltraRequest req = ultraRequestRepository.findById(id).orElse(null);
        if (req == null) {
            return ResponseEntity.status(404).body(Map.of("success", false, "error", "Request not found"));
        }

        String newStatus = body.get("status");
        if (newStatus == null || newStatus.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "error", "Missing status"));
        }

        req.setStatus(newStatus.trim().toUpperCase(Locale.ROOT));
        ultraRequestRepository.save(req);

        return ResponseEntity.ok(Map.of("success", true, "data", req));
    }

    @GetMapping("/queue")
    public List<String> queue() {
        try {
            List<String> items = redisTemplate.opsForList().range("ultra_requests", 0, -1);
            return items == null ? List.of() : items;
        } catch (Exception e) {
            System.out.println("⚠️ Redis queue read failed: " + e.getMessage());
            return List.of();
        }
    }

}
