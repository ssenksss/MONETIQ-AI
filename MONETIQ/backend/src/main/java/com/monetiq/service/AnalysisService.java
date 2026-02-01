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
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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
        if (u.isEmpty()) {
            throw new IllegalArgumentException("Username is required.");
        }

        var profile = profileRepo.findByUsername(u).orElse(null);
        if (profile == null) {
            profile = new com.monetiq.model.Profile();
            profile.setUsername(u);
            profile.setPlatform("instagram");
            profile.setAnalysisDate(LocalDateTime.now());
            profile = profileRepo.save(profile);
            System.out.println("PROFILE AUTO-CREATED for " + u);
        }

        List<String> prev = profile.getSuggestions() == null ? List.of() : profile.getSuggestions();
        List<String> suggestions = pick3Suggestions(u, prev);

        profile.setSuggestions(suggestions);
        profile.setAnalysisDate(LocalDateTime.now());
        profileRepo.save(profile);

        String plan = buildFreeTeaserPlan(u, suggestions);

        AnalysisResponseDTO dto = new AnalysisResponseDTO(u, plan, "FREE", suggestions);

        CreatorAnalysisDoc doc = mongoRepo.findByUsername(u).orElse(null);
        if (doc == null) {
            doc = new CreatorAnalysisDoc();
            doc.setProfileId(profile.getId());
            doc.setUsername(u);
            doc.setPlatform(profile.getPlatform());
            doc.setTier("FREE");
        }

        Map<String, Object> analysis = doc.getAnalysis() == null
                ? new HashMap<>()
                : new HashMap<>(doc.getAnalysis());

        analysis.put("plan", plan);
        analysis.put("suggestions", suggestions);
        analysis.put("nicheGuess", guessNicheFromUsername(u).name());
        doc.setAnalysis(analysis);

        doc.setProfile(Map.of("source", "manual", "data", Map.of("username", u)));
        doc.setMonetization(Map.of("level", "free-teaser", "note", "rule-based suggestions"));

        CreatorAnalysisDoc.Metadata md = doc.getMetadata() == null
                ? new CreatorAnalysisDoc.Metadata()
                : doc.getMetadata();

        md.setGeneratedBy("free-teaser-generator");
        md.setCreatedAt(Instant.now());
        md.setVersion("v2");
        doc.setMetadata(md);

        mongoRepo.save(doc);

        cache.put(u, dto, Duration.ofMinutes(10));

        System.out.println("GENERATE FREE (fresh 3) -> updating analysis_date for " + profile.getUsername());
        return dto;
    }


    private List<String> pick3Suggestions(String username, List<String> prev) {
        Niche niche = guessNicheFromUsername(username);
        Map<Niche, List<String>> pools = suggestionPool();

        Set<String> prevSet = new HashSet<>(prev);

        for (int attempt = 0; attempt < 5; attempt++) {
            List<String> pool = new ArrayList<>(
                    pools.getOrDefault(niche, pools.get(Niche.GENERAL))
            );

            long salt = (System.nanoTime() * 31L)
                    ^ ThreadLocalRandom.current().nextLong()
                    ^ (long) username.hashCode()
                    ^ attempt;

            Collections.shuffle(pool, new Random(salt));
            List<String> res = pool.subList(0, Math.min(3, pool.size()));

            if (!new HashSet<>(res).equals(prevSet)) {
                return res;
            }
        }

        List<String> pool = new ArrayList<>(pools.getOrDefault(niche, pools.get(Niche.GENERAL)));
        Collections.shuffle(pool, new Random(System.nanoTime()));
        return pool.subList(0, Math.min(3, pool.size()));
    }

    public AnalysisResponseDTO getExisting(String username) {
        String u = username == null ? "" : username.trim();
        if (u.isEmpty()) return null;

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

        List<String> suggestions = extractSuggestions(existing);

        AnalysisResponseDTO dto = new AnalysisResponseDTO(
                existing.getUsername(),
                extractPlan(existing),
                existing.getTier() == null ? "FREE" : existing.getTier(),
                suggestions
        );

        cache.put(u, dto, Duration.ofMinutes(10));

        if (profile != null) {
            profile.setAnalysisDate(LocalDateTime.now());
            profileRepo.save(profile);
        }

        return dto;
    }

    private String buildFreeTeaserPlan(String username, List<String> suggestions) {
        var niche = guessNicheFromUsername(username).name();

        StringBuilder sb = new StringBuilder();
        sb.append("Free Teaser for @").append(username).append("\n");
        sb.append("Niche guess: ").append(niche).append("\n\n");

        for (int i = 0; i < suggestions.size(); i++) {
            sb.append(i + 1).append(". ").append(suggestions.get(i)).append("\n");
        }

        sb.append("\nUpgrade to PREMIUM to get a full 30-day monetization plan (DOCX).\n");
        return sb.toString();
    }

    private String extractPlan(CreatorAnalysisDoc doc) {
        if (doc.getAnalysis() == null) return "";
        Object plan = doc.getAnalysis().get("plan");
        return plan == null ? "" : String.valueOf(plan);
    }

    private List<String> extractSuggestions(CreatorAnalysisDoc doc) {
        if (doc.getAnalysis() == null) return List.of();

        Object raw = doc.getAnalysis().get("suggestions");
        if (raw == null) return List.of();

        if (raw instanceof List<?> list) {
            List<String> out = new ArrayList<>();
            for (Object o : list) out.add(String.valueOf(o));
            return out;
        }

        return List.of();
    }

    private enum Niche {
        FITNESS, BEAUTY, FINANCE, TRAVEL, EDUCATION, FAMILY, HEALTH, COOKING, GENERAL
    }

    private Niche guessNicheFromUsername(String u) {
        String s = u.toLowerCase(Locale.ROOT);

        if (s.contains("fit") || s.contains("gym") || s.contains("coach") || s.contains("workout")) return Niche.FITNESS;
        if (s.contains("beauty") || s.contains("skin") || s.contains("makeup") || s.contains("glow") || s.contains("hair")) return Niche.BEAUTY;
        if (s.contains("finance") || s.contains("money") || s.contains("invest") || s.contains("crypto") || s.contains("stocks")) return Niche.FINANCE;
        if (s.contains("travel") || s.contains("nomad") || s.contains("wander") || s.contains("trip") || s.contains("explore")) return Niche.TRAVEL;
        if (s.contains("learn") || s.contains("study") || s.contains("teach") || s.contains("school") || s.contains("academy")) return Niche.EDUCATION;
        if (s.contains("mom") || s.contains("dad") || s.contains("family") || s.contains("mama") || s.contains("tata") || s.contains("kids")) return Niche.FAMILY;
        if (s.contains("health") || s.contains("wellness") || s.contains("mind") || s.contains("therapy") || s.contains("sleep")) return Niche.HEALTH;
        if (s.contains("cook") || s.contains("kitchen") || s.contains("recipe") || s.contains("food") || s.contains("chef")) return Niche.COOKING;

        return Niche.GENERAL;
    }

    private Map<Niche, List<String>> suggestionPool() {
        Map<Niche, List<String>> m = new EnumMap<>(Niche.class);

        m.put(Niche.FITNESS, List.of(
                "Offer idea: 7-day starter plan → DM keyword 'PLAN' funnel.",
                "Reel idea: 3 mistakes killing fat loss (single CTA: save).",
                "Monetize fast: affiliate (equipment/supps) + €9 template pack.",
                "Weekly proof post: progress story + what changed + CTA.",
                "Hook: Stop doing THIS if you want results…",
                "Content pillar: form cues for beginners (quick demos).",
                "Story sequence 2x/week: pain → solution → CTA.",
                "Build waitlist: ‘next cohort’ pinned post + comment CTA.",
                "Carousel: 3-day full-body split checklist (save CTA).",
                "Reel: high-protein breakfast in 60 seconds.",
                "Post: what I eat in a day (simple, no medical claims).",
                "CTA training: 1 post/week ‘Comment PLAN’ to trigger engagement.",
                "Offer stack: freebie → mini offer → 4-week program.",
                "Create a bonus: share + tag = free PDF upgrade.",
                "Weekly Q&A stories; save as Highlight “Q&A”.",
                "Add Highlight “Start here” with freebie + next step.",
                "Reel: top 3 exercises for X (save CTA).",
                "Series: Myth vs Fact (1/week) with short evidence.",
                "Use 1 signature CTA: DM keyword + auto-reply message template.",
                "Make one ‘beginner roadmap’ carousel (pin it).",
                "Create a simple lead magnet: workout tracker sheet.",
                "Add a ‘results timeline’ post: what to expect in 7/14/30 days.",
                "Reel: common form mistake + correction side-by-side.",
                "Story: poll ‘goal’ → next post answers #1 goal.",
                "Affiliate angle: ‘gear I actually use’ post with honest constraints."
        ));

        m.put(Niche.BEAUTY, List.of(
                "Content system: 1 wear-test Reel + 1 ingredient carousel weekly.",
                "Monetize: affiliate picks + UGC bundles (3 videos/package).",
                "Offer: routine checklist freebie → €9 routine builder template.",
                "Hook: Your skincare routine is missing this…",
                "Carousel: budget vs luxury swap (high saves).",
                "Trust builder: show lighting + routine steps clearly.",
                "CTA: DM keyword 'GLOW' for routine checklist.",
                "Reel: AM routine in 20 seconds (order matters).",
                "Post: 3 products I’d buy again (affiliate-friendly).",
                "Ingredient post: what niacinamide does (simple, non-medical).",
                "UGC pitch: Highlight “UGC Samples” + email in bio.",
                "Story: poll skin concern → next day Reel answers #1.",
                "Series: one product, 3 looks (weekly).",
                "CTA rotation: save/share value → DM keyword conversion.",
                "Monthly: favorites list carousel (pin the best one).",
                "Reel: do/don’t application tip (prevents common fails).",
                "Post: ‘how long to test a product’ guide (trust).",
                "Reel: fragrance/texture close-up + quick verdict.",
                "Carousel: routine for oily/dry/sensitive (3 slides each).",
                "Story: mini “routine audit” (before → after improvements).",
                "Offer idea: paid ‘routine audit’ slots (limited weekly).",
                "Reel: ‘3 signs this isn’t working for you’ (general).",
                "Create a ‘starter kit’ recommendation list for niche.",
                "Brand deal tip: create rate card + media kit link.",
                "Hook: I tested this so you don’t have to."
        ));

        m.put(Niche.FINANCE, List.of(
                "Lead magnet: budget template → €9 tracker → €59 money system.",
                "Carousel: ‘Do this in 10 minutes’ money check-in (save CTA).",
                "Authority: weekly ‘money mistakes’ Reel series.",
                "Affiliate: promote tools only after 2–3 trust posts.",
                "Hook: If I started over financially, I’d do this first…",
                "Offer: debt payoff plan template pack (tripwire).",
                "Content pillar: 1 simple framework per week (repeatable).",
                "Reel: 3 habits that improved my finances (non-hype).",
                "Carousel: ‘50/30/20’ breakdown + how to adapt it.",
                "Post: ‘my 3 bank accounts system’ (general concept).",
                "CTA: comment ‘BUDGET’ to receive template link.",
                "Story: quiz “what’s your biggest leak?” → answer next post.",
                "Post: ‘expenses audit’ checklist (high saves).",
                "Reel: ‘how to build an emergency fund’ step-by-step.",
                "Offer: monthly money challenge (free → upsell).",
                "Carousel: ‘investing basics’ vocabulary (simple terms).",
                "Post: ‘subscriptions I canceled’ story + takeaway.",
                "Reel: ‘how I plan my paycheck’ (framework only).",
                "CTA strategy: 2 value posts then 1 offer post.",
                "Build email list: ‘weekly money plan’ newsletter signup.",
                "Product idea: calculator sheet + tutorial (premium).",
                "Post: ‘money myth vs reality’ (1/week).",
                "Reel: ‘what to do with €100 extra’ options by goals.",
                "Story: “rate my budget” (anonymous examples).",
                "Offer: 30-day money reset plan as premium upsell."
        ));

        m.put(Niche.TRAVEL, List.of(
                "Product: mini city guide (€9) → full itinerary guide (€39).",
                "Weekly mix: 1 inspiration Reel + 1 logistics carousel + 1 deal story.",
                "Affiliate: booking tools + gear tied to specific posts.",
                "Freebie: packing checklist → CTA in stories 2x/week.",
                "Hook: Don’t visit without knowing this…",
                "Pillar: hidden gems + budget hacks + itinerary breakdowns.",
                "Reel: ‘best time to visit’ + quick pros/cons.",
                "Carousel: ‘3-day itinerary’ with map-like structure.",
                "Story: ‘cost breakdown’ (hotel/food/transport) transparency.",
                "Post: ‘mistakes tourists make’ (high engagement).",
                "Reel: ‘how to pack in a carry-on’ (quick steps).",
                "Offer: ‘weekend plan’ template you can reuse per city.",
                "CTA: comment ‘GUIDE’ to get the city PDF link.",
                "Story: poll destination → next post is itinerary.",
                "Carousel: ‘airport survival checklist’ (saves).",
                "Reel: ‘top 3 foods to try’ (local angle).",
                "Post: ‘how I plan trips’ (tools + process, affiliate).",
                "Series: ‘€50/day challenge’ in a destination.",
                "Reel: hotel room + what I’d do differently (honest review).",
                "Post: ‘transport hacks’ (metro passes, walking routes).",
                "Content: ‘1 place, 5 photo spots’ (creator-friendly).",
                "Offer: personalized itinerary inquiry (upsell).",
                "Story: packing/booking Q&A weekly (Highlight).",
                "Carousel: ‘avoid scams’ tips (general safety).",
                "Hook: How to travel cheaper without sacrificing comfort."
        ));

        m.put(Niche.EDUCATION, List.of(
                "Productize: notes/templates (€9) → mini course (€49).",
                "Carousel: framework (steps + example + save CTA).",
                "Freebie: study system one-pager → comment ‘SYSTEM’.",
                "Weekly theme: one topic, 3 posts, 1 recap story.",
                "Hook: Most people learn this the wrong way…",
                "Proof: student wins / before-after outcomes (general).",
                "Reel: ‘1 concept explained in 30 seconds’ micro-lesson.",
                "Carousel: common mistakes + corrections checklist.",
                "Story: quiz question → answer next story (engagement).",
                "Post: ‘how I take notes’ template walkthrough.",
                "Offer: exam prep pack (tripwire) + upsell course.",
                "CTA: DM keyword ‘NOTES’ for template link.",
                "Pillar: examples (show solved problems step-by-step).",
                "Reel: ‘study with me’ 15 seconds + tip overlay.",
                "Post: ‘best apps/tools’ list (optional affiliate).",
                "Carousel: revision timetable for 7/14/30 days.",
                "Series: ‘define this in simple terms’ (daily/weekly).",
                "Story: ask for topic requests → batch content.",
                "Pin: ‘Start here’ + ‘How to use templates’ + ‘Offer’.",
                "Post: ‘how to stop procrastinating’ (framework only).",
                "Reel: ‘memory trick’ + example (short).",
                "Carousel: ‘learning roadmap’ for beginners (save).",
                "Offer: small-group cohort (limited seats) as premium.",
                "Post: ‘what I’d do if I had 2 hours to study’ plan.",
                "Hook: Here’s the framework I wish I had earlier."
        ));

        m.put(Niche.FAMILY, List.of(
                "Digital: routines + weekly planners (printables).",
                "Content mix: relatable story → practical tip → product solution.",
                "Freebie: weekly family planner → DM ‘PLANNER’.",
                "Affiliate: tie products to pain-point posts (sleep/meals/routines).",
                "Carousel: bedtime routine checklist (save CTA).",
                "Hook: No one tells parents this, but…",
                "Post: ‘morning routine for school days’ step-by-step.",
                "Story: poll ‘biggest struggle’ → next post answers #1.",
                "Reel: ‘5 minute tidy reset’ with timer (realistic).",
                "Carousel: ‘meal plan for busy week’ (shopping list).",
                "Offer: paid printable pack (tripwire) + membership library.",
                "Post: ‘screen time boundaries’ (gentle tips).",
                "Reel: ‘lunchbox ideas’ quick montage.",
                "Story: ‘family calendar’ template walkthrough.",
                "Carousel: ‘family budget meals’ (cross with cooking).",
                "Highlight: ‘Start here’ + ‘Routines’ + ‘Printables’.",
                "Post: ‘what worked for us’ weekly win story.",
                "Reel: ‘after-school routine’ in 3 steps.",
                "CTA: comment ‘ROUTINE’ to get checklist link.",
                "Offer stack: freebie → printables → full system guide.",
                "Post: ‘kids chores chart’ template (save).",
                "Reel: ‘stress-free mornings’ 3 tips.",
                "Carousel: ‘travel with kids’ checklist (bonus).",
                "Story: Q&A box every Sunday (consistency).",
                "Post: ‘how we plan the week in 10 minutes’ framework."
        ));

        m.put(Niche.HEALTH, List.of(
                "Freebie: 7-day habit reset tracker (link in bio).",
                "Post rhythm: 2 habit tips + 1 myth-busting post weekly.",
                "Offer: €9 habit reset plan → core wellness guide.",
                "Hook: This improved my energy in 7 days…",
                "Gentle CTA: save/share before DM keywords.",
                "Pillar: sleep → stress → nutrition basics (general).",
                "Reel: ‘morning routine for better energy’ 3 steps.",
                "Carousel: ‘sleep hygiene checklist’ (save).",
                "Story: daily reminder prompt + habit check-in.",
                "Post: ‘hydration mistakes’ (simple fixes).",
                "Reel: ‘1-minute breathing reset’ (general wellness).",
                "Carousel: ‘stress signals’ + what to do (non-medical).",
                "CTA: DM ‘RESET’ for habit tracker link.",
                "Offer: 30-day habit challenge (premium upsell).",
                "Post: ‘walk more’ framework: steps ladder over weeks.",
                "Reel: ‘healthy snack ideas’ quick list.",
                "Story: poll “sleep hours” → follow-up tip.",
                "Post: ‘how to build consistency’ (identity habits).",
                "Carousel: ‘energy-friendly meals’ basics (general).",
                "Reel: ‘evening routine’ for better sleep.",
                "Highlight: ‘Start here’ + ‘Tracker’ + ‘Routine’.",
                "Post: weekly reflection prompt (community).",
                "Reel: ‘stop doing this at night’ tip (general).",
                "Carousel: ‘habit stacking examples’ (save).",
                "Offer: accountability group interest form (ultra lead)."
        ));

        m.put(Niche.COOKING, List.of(
                "Monetize: meal plans + printable shopping lists.",
                "Pillars: quick dinners, meal prep workflow, flavor hacks.",
                "High reach: 15s recipe Reels with 1 clear hook.",
                "CTA: save/share growth posts → link to meal plan later.",
                "Affiliate: kitchen tools only shown in-use in recipe posts.",
                "Hook: This saves me 30 minutes every day…",
                "Reel: ‘3 ingredients dinner’ quick demo.",
                "Carousel: weekly menu + shopping list (save).",
                "Post: ‘how to meal prep in 60 minutes’ checklist.",
                "Reel: ‘knife skill tip’ + why it matters.",
                "Post: ‘spice combo of the week’ (repeatable series).",
                "Offer: €9 meal prep template + upsell monthly plan.",
                "CTA: comment ‘MEAL’ for shopping list PDF.",
                "Story: poll ‘what should I cook?’ → next day recipe.",
                "Reel: ‘one-pan meal’ step-by-step.",
                "Carousel: ‘budget meals’ (cost per serving).",
                "Reel: ‘protein meal ideas’ quick list.",
                "Post: ‘how to fix bland food’ flavor hacks.",
                "Highlight: ‘Start here’ + ‘Meal plans’ + ‘Tools’.",
                "Reel: ‘lunch in 10 minutes’ montage.",
                "Carousel: ‘freezer staples’ list (save).",
                "Post: ‘healthy swaps’ (general, no medical claims).",
                "Reel: ‘dessert hack’ quick recipe.",
                "Post: ‘kitchen essentials’ list (affiliate friendly).",
                "Offer: recipe subscription (premium) + weekly email."
        ));

        m.put(Niche.GENERAL, List.of(
                "Pick 3 content pillars and stick to them for 14 days.",
                "Add 1-line bio CTA: who you help + next step.",
                "Create a checklist/template freebie to start a list.",
                "Weekly theme system: one topic, multiple angles.",
                "Monetize path: affiliate + simple digital product + brand deals.",
                "Pin 3 posts: intro, proof, and your offer/CTA.",
                "Use one DM keyword CTA to simplify conversion.",
                "Create a ‘Start Here’ Highlight with your best links.",
                "Post 1 proof post weekly (results, case study, testimonial).",
                "Batch-create 10 hooks and reuse them across formats.",
                "Use carousels for saves, Reels for reach, Stories for conversion.",
                "Make content modular: hook → value → CTA.",
                "Create a mini-offer (€9) that solves one small pain point.",
                "Do a weekly Q&A story and turn answers into posts.",
                "Offer stack: freebie → mini offer → core offer.",
                "Create a 7-day challenge to drive engagement.",
                "Add a weekly recap post (what worked, what didn’t).",
                "Use polls to pick next content topics (audience-led).",
                "Create a simple landing page for your freebie.",
                "Repurpose: one long post → 3 short posts.",
                "Make a ‘mistakes to avoid’ series (repeatable).",
                "Build an email list early; monetize later.",
                "Clarify positioning: who you help + how + outcome.",
                "Use strong hooks: pain + promise + proof (when possible).",
                "Keep CTAs single: one action per post."
        ));

        return m;
    }
}
