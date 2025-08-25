public class Condition {
    public String metricGreaterThan;   // key in ctx.metrics (Double)
    public Double value;               // threshold
    public String eventPresent;        // value in ctx.recentEvents
    public String controlMissing;      // value NOT in ctx.controls
    public String controlPresent;      // value in ctx.controls
    public String segmentPresent;      // "SPACE" | "GROUND" | "LINK"
    public String configEqualsKey;     // key in ctx.config
    public String configEqualsValue;   // expected value

    public boolean evaluate(SystemContext ctx) {
        // segmentPresent
        if (segmentPresent != null) {
            try {
                Segment s = Segment.valueOf(segmentPresent);
                if (ctx.segments == null || !ctx.segments.contains(s)) return false;
            } catch (IllegalArgumentException e) { return false; }
        }

        // eventPresent
        if (eventPresent != null) {
            if (ctx.recentEvents == null || !ctx.recentEvents.contains(eventPresent)) return false;
        }

        // controlMissing
        if (controlMissing != null) {
            if (ctx.controls != null && ctx.controls.contains(controlMissing)) return false;
        }

        // controlPresent
        if (controlPresent != null) {
            if (ctx.controls == null || !ctx.controls.contains(controlPresent)) return false;
        }

        // metricGreaterThan
        if (metricGreaterThan != null) {
            if (ctx.metrics == null) return false;
            Double v = ctx.metrics.get(metricGreaterThan);
            if (v == null || value == null || !(v > value)) return false;
        }

        // configEquals
        if (configEqualsKey != null) {
            if (ctx.config == null) return false;
            String v = ctx.config.get(configEqualsKey);
            if (v == null || !v.equals(configEqualsValue)) return false;
        }

        return true;
    }
}



