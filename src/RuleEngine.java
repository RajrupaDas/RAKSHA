import java.util.*;

public class RuleEngine {
    public List<Recommendation> evaluate(RuleSet set, SystemContext ctx) {
        List<Recommendation> out = new ArrayList<>();
        for (Rule r : set.rules) {
            if (r.matches(ctx)) {
                out.add(toRecommendation(r));
            }
        }
        return out;
    }

    private Recommendation toRecommendation(Rule r) {
        return new Recommendation(
                r.then.severity,
                r.then.message,
                r.then.recommendControls,
                r.then.actions,
                r.riskDelta
        );
    }

    // --- NEW: severity-weighted resilience score ---
    public int calculateResilienceScore(List<Recommendation> recs) {
        if (recs.isEmpty()) return 100; // Perfect resilience if no findings

        int totalPenalty = 0;
        for (Recommendation r : recs) {
            int weight = switch (r.severity()) {
                case "CRITICAL" -> 5;
                case "HIGH" -> 3;
                case "MEDIUM" -> 2;
                default -> 1;
            };
            totalPenalty += r.riskDelta() * weight;
        }

        // Normalize into 0–100 (simple heuristic)
        int score = 100 - Math.min(totalPenalty, 100);
        return score;
    }
}

