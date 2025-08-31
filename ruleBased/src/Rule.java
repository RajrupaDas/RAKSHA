import java.util.List;

public class Rule {
    public String id;
    public String description;
    public List<AnyAll> when;
    public List<String> requireControls;
    public List<String> segments;   // "SPACE", "GROUND", "LINK"
    public int riskDelta = 0;
    public Then then;

    // Check segments, required controls, and all/any conditions
    public boolean matches(SystemContext ctx) {
        if (!appliesToSegments(ctx)) return false;
        if (!missingRequiredControls(ctx)) return false;
        if (!evaluateWhen(ctx)) return false;
        return true;
    }

    private boolean appliesToSegments(SystemContext ctx) {
        if (segments == null || segments.isEmpty()) return true;
        if (ctx.segments == null) return false;
        for (String s : segments) {
            try {
                Segment seg = Segment.valueOf(s);
                if (ctx.segments.contains(seg)) return true;
            } catch (IllegalArgumentException ignore) {}
        }
        return false;
    }

    private boolean missingRequiredControls(SystemContext ctx) {
        if (requireControls == null || requireControls.isEmpty()) return true;
        if (ctx.controls == null) return true; // treat as missing
        for (String c : requireControls) {
            if (!ctx.controls.contains(c)) return true;
        }
        return false;
    }

    private boolean evaluateWhen(SystemContext ctx) {
        if (when == null || when.isEmpty()) return true; // no predicates
        for (AnyAll clause : when) {
            if (!clause.evaluate(ctx)) return false;
        }
        return true;
    }
}

