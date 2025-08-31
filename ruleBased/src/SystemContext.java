import java.util.List;
import java.util.Map;
import java.util.Set;

public class SystemContext {
    public String missionName;
    public Set<Segment> segments;
    public Set<String> controls;
    public Map<String, Double> metrics;
    public Map<Segment, Boolean> backups;
    public List<String> recentEvents;
    public Map<String, String> config;
}

