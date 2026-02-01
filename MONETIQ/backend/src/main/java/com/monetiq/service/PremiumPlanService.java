package com.monetiq.service;

import com.monetiq.model.dto.PremiumDayDTO;
import com.monetiq.model.dto.PremiumItemDTO;
import com.monetiq.model.dto.PremiumPlanResponseDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class PremiumPlanService {

    private enum Niche {
        FITNESS, BEAUTY, FINANCE, TRAVEL, EDUCATION, FAMILY, HEALTH, COOKING, GENERAL
    }

    public PremiumPlanResponseDTO generate(String username) {
        String u = username == null ? "" : username.trim().replace("@", "");
        if (u.isEmpty()) throw new IllegalArgumentException("Username is required.");

        Niche niche = guessNicheFromUsername(u);

        String planText = buildProPlanText(u, niche);
        List<PremiumDayDTO> days = build30Days(u, niche);

        List<PremiumItemDTO> items = new ArrayList<>();
        for (PremiumDayDTO d : days) {
            String oneLiner = d.getTitle() + " — " + (d.getTasks() != null && !d.getTasks().isEmpty() ? d.getTasks().get(0) : "");
            items.add(new PremiumItemDTO(d.getDay(), oneLiner));
        }

        return new PremiumPlanResponseDTO(u, "PREMIUM", planText, days, items);
    }

    private String buildProPlanText(String username, Niche niche) {

        record Copy(String audience, String positioningBlock, String offer, String ctaLine, String keyword) {}

        Copy copy = switch (niche) {

            case FITNESS -> new Copy(
                    "busy people who want visible results without extreme diets or confusing routines",
                    """
You are not a fitness influencer.
You are a decision filter.

Your positioning:
“I help busy people get in shape using simple, repeatable systems.”

People don’t want workouts.
They want certainty.

Your job is to remove:
- confusion
- overthinking
- inconsistency
""",
                    "12-week body transformation system",
                    "DM ‘FIT’ to start",
                    "FIT"
            );

            case COOKING -> new Copy(
                    "busy people who want to eat well without thinking about meals every day",
                    """
You are not a recipe page.
You are a lifestyle simplifier.

Your positioning:
“I remove daily food decisions using a repeatable meal system.”

People don’t need more recipes.
They need fewer decisions.
""",
                    "Weekly meal system + shopping lists",
                    "Comment ‘MEAL’ for the shopping list / system",
                    "MEAL"
            );

            case BEAUTY -> new Copy(
                    "women who want consistent results, not viral hacks",
                    """
You are not a trend follower.
You are a routine architect.

Your positioning:
“I help women build routines that actually work long-term.”

Your content should feel:
- calm
- structured
- confident
""",
                    "Personal routine + product system",
                    "DM ‘ROUTINE’ to start",
                    "ROUTINE"
            );

            case FINANCE -> new Copy(
                    "young professionals trying to escape paycheck-to-paycheck living",
                    """
You are not a motivation page.
You are a clarity provider.

Your positioning:
“I help people build control over money using simple rules.”

Complexity kills action.
Simplicity scales.
""",
                    "Step-by-step money system",
                    "DM ‘PLAN’ to get started",
                    "PLAN"
            );

            case TRAVEL -> new Copy(
                    "people who want memorable trips without planning stress",
                    """
You are not a travel inspiration page.
You are a shortcut.

Your positioning:
“I help people travel smarter with ready-to-use itineraries.”

People don’t want photos.
They want certainty + logistics.
""",
                    "Itinerary packs + booking checklist",
                    "Comment ‘GUIDE’ to get the itinerary framework",
                    "GUIDE"
            );

            case EDUCATION -> new Copy(
                    "students and beginners overwhelmed by too much information",
                    """
You are not a lesson page.
You are a translator.

Your positioning:
“I turn complexity into clear steps that beginners can execute.”

Your goal is not to teach everything.
Your goal is to create momentum.
""",
                    "Structured learning roadmap + templates",
                    "DM ‘STUDY’ to get the roadmap",
                    "STUDY"
            );

            case HEALTH -> new Copy(
                    "people who want habits that actually stick (non-medical content)",
                    """
You are not a wellness quote page.
You are a habit engineer.

Your positioning:
“I help people build health routines they can sustain.”

Consistency beats intensity.
""",
                    "Habit system + tracking templates",
                    "DM ‘HABIT’ to start",
                    "HABIT"
            );

            case FAMILY -> new Copy(
                    "parents who want calmer routines and less daily chaos",
                    """
You are not a parenting meme page.
You are a routine designer.

Your positioning:
“I help families create calm routines that reduce friction.”

Your content should remove guilt
and add structure.
""",
                    "Family routine system + weekly planners",
                    "Comment ‘CALM’ to get the planner",
                    "CALM"
            );

            default -> new Copy(
                    "people who feel stuck and want a clear next step",
                    """
You are not entertainment.
You are direction.

Your positioning:
“I help people move from confusion to execution.”

Clarity converts.
""",
                    "Structured implementation program",
                    "DM ‘START’ to begin",
                    "START"
            );
        };

        return """
MONETIQ PREMIUM — MONETIZATION STRATEGY
Creator: @%s

You are not building an audience.
You are building a DECISION-MAKING MACHINE.

--------------------------------
1) POSITIONING (non-negotiable)
--------------------------------
%s

Your audience:
%s

Bio structure:
- Who you help
- The result you get them
- The mechanism
- CTA (DM keyword)

CTA example:
%s

--------------------------------
2) CONTENT THAT MOVES MONEY
--------------------------------
Stop posting to entertain.

Post to:
- Surface problems (pain, mistakes, myths)
- Reframe beliefs (authority)
- Show proof (results, systems)
- Convert (clear next step)

Your content buckets:
- Problem awareness
- Authority
- Proof
- Conversion

--------------------------------
3) YOUR OFFER
--------------------------------
Primary offer:
%s

This is NOT optional.
Every serious creator has an offer.

--------------------------------
4) CONVERSION FLOW
--------------------------------
Reels → Reach
Carousels → Saves
Stories → Trust
DMs → Sales

CTA ladder:
Save → Follow → Comment/DM keyword → Apply / Checkout

--------------------------------
5) NEXT STEP
--------------------------------
Execute the 30-day plan below.
No skipping days.
Momentum beats perfection.
""".formatted(
                username,
                copy.positioningBlock,
                copy.audience,
                copy.ctaLine,
                copy.offer
        );
    }


    private List<PremiumDayDTO> build30Days(String username, Niche niche) {

        List<PremiumDayDTO> days = new ArrayList<>();

        for (int day = 1; day <= 30; day++) {

            String contentType;

            if (day >= 15 && day <= 21) {
                if (day % 2 == 0) contentType = "Story";
                else if (day % 3 == 0) contentType = "Carousel";
                else contentType = "Reel";
            } else {
                if (day % 7 == 0) contentType = "Story";
                else if (day % 3 == 0) contentType = "Carousel";
                else contentType = "Reel";
            }
            String title;
            List<String> tasks = new ArrayList<>();

            if (day == 1) {
                title = "IDENTITY RESET";
                tasks.add(hook(niche, "If you’re stuck, it’s not discipline — it’s identity.", "Most people fail because they don’t know who they’re becoming."));
                tasks.add(execution(niche, "Rewrite your bio into ONE promise + ONE outcome.", "Define who this account is for and who it is NOT for."));
                tasks.add(cta(niche));
            } else if (day == 2) {
                title = "AUDIENCE PAIN MAP";
                tasks.add(hook(niche, "Your audience doesn’t buy solutions. They buy relief.", "If you don’t understand pain, you don’t understand money."));
                tasks.add(execution(niche, "List 10 frustrations your audience complains about.", "Create 3 post ideas from that list immediately."));
                tasks.add(cta(niche));
            } else if (day == 3) {
                title = "AUTHORITY DISRUPTION";
                tasks.add(hook(niche, "Most advice online is wrong. Here’s why.", "This is why beginners stay beginners."));
                tasks.add(execution(niche, "Post an unpopular opinion that challenges mainstream advice.", "Use: myth → why it’s wrong → what to do instead."));
                tasks.add("CTA: End with “Save this — you’ll need it.”");
            } else if (day == 4) {
                title = "PROBLEM AWARENESS";
                tasks.add(hook(niche, "You don’t have a motivation problem.", "You have a system problem."));
                tasks.add(execution(niche, "Explain WHY the problem exists.", "Do NOT give the full solution yet (create anticipation)."));
                tasks.add("CTA: “Follow for part 2.”");
            } else if (day == 5) {
                title = "MISTAKE AMPLIFICATION";
                tasks.add(hook(niche, "If you’re doing this, stop.", "This mistake keeps people stuck for years."));
                tasks.add(execution(niche, "List 3 beginner mistakes.", "Frame it as: ‘If you fix this, everything changes.’"));
                tasks.add(cta(niche));
            } else if (day == 6) {
                title = "FRAMEWORK DROP";
                tasks.add(hook(niche, "You don’t need more effort. You need a framework.", "This 3-step system fixes everything."));
                tasks.add(execution(niche, "Introduce a simple 3-step framework.", "Explain ONLY Step 1 deeply and tease Step 2/3."));
                tasks.add("CTA: “Save this.”");
            } else if (day == 7) {
                title = "TRUST LAYER";
                tasks.add(hook(niche, "Nobody talks about this part.", "Here’s what changed everything for me."));
                tasks.add(execution(niche, "Share a personal lesson or story.", "Tie it directly to your framework."));
                tasks.add(cta(niche));
            }

            else if (day == 8) {
                title = "EXPECTATION RESET";
                tasks.add(hook(niche, "If you expect fast results, this isn’t for you.", "Real results follow rules, not moods."));
                tasks.add(execution(niche, "Explain realistic timelines in your niche.", "Call out the #1 unrealistic expectation people have."));
                tasks.add(cta(niche));
            } else if (day == 9) {
                title = "MICRO PROOF";
                tasks.add(hook(niche, "This small change works every time.", "Most people ignore it because it’s boring."));
                tasks.add(execution(niche, "Show a tiny win or a repeatable pattern.", "Explain WHY it works in one sentence."));
                tasks.add(cta(niche));
            } else if (day == 10) {
                title = "SYSTEM > TACTICS";
                tasks.add(hook(niche, "Tactics don’t scale. Systems do.", "Here’s what pros do differently."));
                tasks.add(execution(niche, "Compare random actions vs structured system.", "Give 1 example of each."));
                tasks.add("CTA: “Save this.”");
            } else if (day == 11) {
                title = "OBJECTION BREAKER";
                tasks.add(hook(niche, "This excuse sounds smart — but it’s fake.", "It’s avoidance dressed as logic."));
                tasks.add(execution(niche, "Address the #1 excuse in your niche.", "Reframe it as a solvable problem."));
                tasks.add(cta(niche));
            } else if (day == 12) {
                title = "WINNER VS AMATEUR";
                tasks.add(hook(niche, "Here’s the difference nobody wants to hear.", "One group wins. One group stays stuck."));
                tasks.add(execution(niche, "Contrast amateurs vs winners in your niche.", "Make it specific and blunt."));
                tasks.add("CTA: “Which one are you?”");
            } else if (day == 13) {
                title = "NON-NEGOTIABLE RULE";
                tasks.add(hook(niche, "I never break this rule.", "It protects results."));
                tasks.add(execution(niche, "Explain one principle you never compromise on.", "Show what happens when people ignore it."));
                tasks.add("CTA: “Follow for more rules.”");
            } else if (day == 14) {
                title = "SOFT OFFER SEED";
                tasks.add(hook(niche, "People keep asking how I help privately.", "So here’s what I do."));
                tasks.add(execution(niche, "Mention you help people 1:1 or via a program.", "Do NOT pitch, just describe outcome."));
                tasks.add(cta(niche));
            }

            else if (day == 15) {
                title = "OFFER INTRO";
                tasks.add(hook(niche, "Here’s exactly what I help people achieve.", "No fluff."));
                tasks.add(execution(niche, "State the outcome in one clear line.", "Add who it’s for."));
                tasks.add(cta(niche));
            } else if (day == 16) {
                title = "PROCESS CLARITY";
                tasks.add(hook(niche, "No magic. Just steps.", "This is the process."));
                tasks.add(execution(niche, "Break your offer into 3–5 clear steps.", "Remove mystery."));
                tasks.add("CTA: “Save this.”");
            } else if (day == 17) {
                title = "WHO IT’S FOR / NOT FOR";
                tasks.add(hook(niche, "This is NOT for everyone.", "And that’s the point."));
                tasks.add(execution(niche, "Define ideal client clearly.", "Exclude people who aren’t serious."));
                tasks.add(cta(niche));
            } else if (day == 18) {
                title = "RISK REVERSAL";
                tasks.add(hook(niche, "The real risk is staying the same.", "Not trying."));
                tasks.add(execution(niche, "Address fear of wasting time/money.", "Show the cost of inaction."));
                tasks.add(cta(niche));
            } else if (day == 19) {
                title = "DM CONVERSION";
                tasks.add(hook(niche, "If you’re serious, read this.", "This is how we start."));
                tasks.add(execution(niche, "Invite a keyword DM/comment.", "Reply with questions, not pitches."));
                tasks.add(cta(niche));
            } else if (day == 20) {
                title = "SCARCITY SIGNAL";
                tasks.add(hook(niche, "I don’t work with everyone.", "Because results need focus."));
                tasks.add(execution(niche, "Mention limited availability (real).", "Explain why you cap it."));
                tasks.add(cta(niche));
            } else if (day == 21) {
                title = "DECISION POINT";
                tasks.add(hook(niche, "Decide now.", "Stay stuck or move forward."));
                tasks.add(execution(niche, "Challenge them to commit.", "Make the CTA explicit."));
                tasks.add(cta(niche));
            }

            else if (day == 22) {
                title = "FAQ DAY";
                tasks.add(hook(niche, "The question I get every day:", "Let’s kill confusion."));
                tasks.add(execution(niche, "Answer the #1 DM question.", "Give short clarity + next step."));
                tasks.add(cta(niche));
            } else if (day == 23) {
                title = "PATTERN PROOF";
                tasks.add(hook(niche, "This is the pattern behind results.", "Once you see it, you can’t unsee it."));
                tasks.add(execution(niche, "Share a repeated win you notice.", "Explain the mechanism."));
                tasks.add(cta(niche));
            } else if (day == 24) {
                title = "LIFESTYLE FIT";
                tasks.add(hook(niche, "This only works if it fits real life.", "Here’s how."));
                tasks.add(execution(niche, "Show your system in a realistic scenario.", "Make it relatable."));
                tasks.add(cta(niche));
            } else if (day == 25) {
                title = "HESITATION REFRAME";
                tasks.add(hook(niche, "If you’re hesitating, read this.", "It’s not what you think."));
                tasks.add(execution(niche, "Turn hesitation into clarity.", "Normalize fear + direct action."));
                tasks.add(cta(niche));
            } else if (day == 26) {
                title = "LONG-TERM THINKING";
                tasks.add(hook(niche, "Quick wins feel good.", "Long wins change your life."));
                tasks.add(execution(niche, "Explain why your method works long-term.", "Contrast quick hacks."));
                tasks.add(cta(niche));
            } else if (day == 27) {
                title = "DIRECT ASK";
                tasks.add(hook(niche, "If you want help, I’ll be direct.", "Here’s how."));
                tasks.add(execution(niche, "Ask serious people to DM/comment.", "Keep it short."));
                tasks.add(cta(niche));
            } else if (day == 28) {
                title = "SOCIAL SIGNAL";
                tasks.add(hook(niche, "People are already moving.", "Don’t be last."));
                tasks.add(execution(niche, "Mention momentum (without lying).", "Invite them in."));
                tasks.add(cta(niche));
            } else if (day == 29) {
                title = "FINAL CALL";
                tasks.add(hook(niche, "Last call.", "Then I close it."));
                tasks.add(execution(niche, "Close intake / pause availability.", "Keep it real."));
                tasks.add(cta(niche));
            } else {
                title = "RESET & LOOP";
                tasks.add(hook(niche, "The cycle is the secret.", "Not motivation."));
                tasks.add(execution(niche, "Review what worked.", "Repeat the winning loop with more authority."));
                tasks.add("CTA: Restart the cycle stronger.");
            }

            days.add(new PremiumDayDTO(day, title, contentType, tasks));
        }

        return days;
    }


    private String hook(Niche niche, String base1, String base2) {

        String spice = switch (niche) {
            case FITNESS -> " (Most people train hard and still look the same.)";
            case COOKING -> " (Decision fatigue is why dinner feels impossible.)";
            case BEAUTY -> " (Consistency beats viral hacks.)";
            case FINANCE -> " (Rules beat motivation.)";
            case TRAVEL -> " (Planning should be simple, not stressful.)";
            case EDUCATION -> " (Clarity creates momentum.)";
            case FAMILY -> " (Structure reduces chaos.)";
            case HEALTH -> " (Habits beat intensity.)";
            default -> "";
        };
        return "Hook: " + base1 + " " + base2 + spice;
    }

    private String execution(Niche niche, String line1, String line2) {
        String extra = switch (niche) {
            case FITNESS -> "Add one ‘form tip’ clip or a beginner checklist for saves.";
            case COOKING -> "Add a ‘shopping list’ angle (ingredients + swaps) for saves.";
            case BEAUTY -> "Add 1 routine step + why it matters (no product dumping).";
            case FINANCE -> "Add 1 simple rule framework (saveable).";
            case TRAVEL -> "Add 1 mini-itinerary / hidden gem list.";
            case EDUCATION -> "Add 1 template / cheat sheet CTA.";
            case FAMILY -> "Add 1 routine template / weekly planner idea.";
            case HEALTH -> "Add 1 tracker template (non-medical) angle.";
            default -> "Make it simple enough to repeat weekly.";
        };
        return "Execution: " + line1 + " " + line2 + " " + extra;
    }

    private String cta(Niche niche) {
        return switch (niche) {
            case FITNESS -> "CTA: DM ‘FIT’ for the starter plan.";
            case COOKING -> "CTA: Comment ‘MEAL’ and I’ll send the shopping list.";
            case BEAUTY -> "CTA: DM ‘ROUTINE’ to get the routine framework.";
            case FINANCE -> "CTA: DM ‘PLAN’ and I’ll send the money system.";
            case TRAVEL -> "CTA: Comment ‘GUIDE’ to get the itinerary framework.";
            case EDUCATION -> "CTA: DM ‘STUDY’ to get the roadmap.";
            case FAMILY -> "CTA: Comment ‘CALM’ to get the routine planner.";
            case HEALTH -> "CTA: DM ‘HABIT’ to get the tracker.";
            default -> "CTA: DM ‘START’ to begin.";
        };
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
}
