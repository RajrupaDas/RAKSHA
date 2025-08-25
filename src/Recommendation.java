import java.util.List;

public class Recommendation {
    public Severity severity;
    public String message;
    public List<String> controls;
    public List<String> actions;
    public int riskDelta;

    public int riskDelta() { return riskDelta; }
}


