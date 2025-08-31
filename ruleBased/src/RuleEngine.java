import java.util.ArrayList;
import java.util.List;

public class RuleEngine {

    public List<Recommendation> evaluate(RuleSet set, SystemContext ctx) {
        List<Recommendation> out = new ArrayList<>();
        if (set == null || set.rules == null) return out;
        for (Rule r : set.rules) {
            if (r.matches(ctx)) {
                out.add(toRecommendation(r));
            }
        }
        return out;
    }

    private Recommendation toRecommendation(Rule r) {
        return new Recommendation(
                r.then != null ? r.then.severity : "LOW",
                r.then != null ? r.then.message : r.description,
                r.then != null ? r.then.recommendControls : List.of(),
                r.then != null ? r.then.actions : List.of(),
                r.riskDelta
        );
    }

    // Severity-weighted resilience score (0–100, higher is better)
    public int calculateResilienceScore(List<Recommendation> recs) {
        if (recs == null || recs.isEmpty()) return 100;
        int totalPenalty = 0;
        for (Recommendation r : recs) {
            int weight;
            String sev = r.getSeverity() == null ? "LOW" : r.getSeverity();
            switch (sev) {
                case "CRITICAL": weight = 5; break;
                case "HIGH":     weight = 3; break;
                case "MEDIUM":   weight = 2; break;
                default:         weight = 1; break;
            }
            totalPenalty += r.getRiskDelta() * weight;
        }
        return 100 - Math.min(totalPenalty, 100);
    }
}

