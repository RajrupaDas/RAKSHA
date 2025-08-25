import java.util.List;

public class Rule {
    public String id;
    public String description;
    public List<AnyAll> when;
    public List<String> requireControls;
    public List<String> segments;
    public int riskDelta = 0;
    public Outcome then;
}

