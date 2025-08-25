import java.util.List;

public class Recommendation {
    private String severity;
    private String message;
    private List<String> recommendControls;
    private List<String> actions;
    private int riskDelta;

    public Recommendation() {}

    public Recommendation(String severity, String message,
                          List<String> recommendControls,
                          List<String> actions,
                          int riskDelta) {
        this.severity = severity;
        this.message = message;
        this.recommendControls = recommendControls;
        this.actions = actions;
        this.riskDelta = riskDelta;
    }

    public String getSeverity() { return severity; }
    public String getMessage() { return message; }
    public List<String> getRecommendControls() { return recommendControls; }
    public List<String> getActions() { return actions; }
    public int getRiskDelta() { return riskDelta; }
}

