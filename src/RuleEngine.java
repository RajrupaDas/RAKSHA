import java.util.*;

public class RuleEngine {
    public List<Recommendation> evaluate(RuleSet set, SystemContext ctx) {
        List<Recommendation> out = new ArrayList<>();
        if (set == null || set.rules == null) return out;

        for (Rule r : set.rules) {
            if (!appliesToSegments(r, ctx)) continue;
            if (!missingRequiredControls(r, ctx)) continue;
            if (evaluateGroups(r.when, ctx)) {
                Recommendation rec = toRecommendation(r);
                out.add(rec);
            }
        }
        return out;
    }

    private boolean appliesToSegments(Rule r, SystemContext ctx) {
        if (r.segments == null || r.segments.isEmpty()) return true;
        if (ctx.segments == null) return false;
        for (String s : r.segments) {
            try {
                Segment seg = Segment.valueOf(s);
                if (ctx.segments.contains(seg)) return true;
            } catch (IllegalArgumentException ex) { }
        }
        return false;
    }

    private boolean missingRequiredControls(Rule r, SystemContext ctx) {
        if (r.requireControls == null || r.requireControls.isEmpty()) return true;
        if (ctx.controls == null) return true;
        for (String c : r.requireControls) {
            if (!ctx.controls.contains(c)) return true;
        }
        return false;
    }

    private boolean evaluateGroups(List<AnyAll> groups, SystemContext ctx) {
        if (groups == null || groups.isEmpty()) return true;
        for (AnyAll g : groups) {
            boolean groupResult = true;
            if (g.all != null && !g.all.isEmpty()) {
                for (Condition c : g.all) {
                    if (!eval(c, ctx)) { groupResult = false; break; }
                }
            }
            if (g.any != null && !g.any.isEmpty()) {
                boolean anyTrue = false;
                for (Condition c : g.any) {
                    if (eval(c, ctx)) { anyTrue = true; break; }
                }
                groupResult = groupResult && anyTrue;
            }
            if (!groupResult) return false;
        }
        return true;
    }

    private boolean eval(Condition c, SystemContext ctx) {
        if (c.metricGreaterThan != null) {
            if (ctx.metrics == null) return false;
            Double val = ctx.metrics.get(c.metricGreaterThan);
            return val != null && c.value != null && val > c.value;
        }
        if (c.eventPresent != null) {
            return ctx.recentEvents != null && ctx.recentEvents.contains(c.eventPresent);
        }
        if (c.controlMissing != null) {
            return ctx.controls == null || !ctx.controls.contains(c.controlMissing);
        }
        if (c.controlPresent != null) {
            return ctx.controls != null && ctx.controls.contains(c.controlPresent);
        }
        if (c.segmentPresent != null) {
            try {
                Segment s = Segment.valueOf(c.segmentPresent);
                return ctx.segments != null && ctx.segments.contains(s);
            } catch (IllegalArgumentException ex) { return false; }
        }
        if (c.configEqualsKey != null) {
            if (ctx.config == null) return false;
            String v = ctx.config.get(c.configEqualsKey);
            return Objects.equals(v, c.configEqualsValue);
        }
        return false;
    }

    private Recommendation toRecommendation(Rule r) {
        Recommendation rec = new Recommendation();
        rec.severity = parseSeverity(r.then != null ? r.then.severity : "LOW");
        rec.message = r.then != null ? r.then.message : r.description;
        rec.controls = r.then != null ? r.then.recommendControls : List.of();
        rec.actions = r.then != null ? r.then.actions : List.of();
        rec.riskDelta = r.riskDelta;
        return rec;
    }

    private Severity parseSeverity(String s) {
        try { return Severity.valueOf(s); } catch (Exception e) { return Severity.LOW; }
    }
}

