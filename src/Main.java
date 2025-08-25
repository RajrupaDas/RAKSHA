import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: java -cp 'lib/*:src' Main rules.yaml scenarios/ddos-ground.json");
            return;
        }

        File rulesFile = new File(args[0]);
        File scenarioFile = new File(args[1]);

        ObjectMapper yaml = new ObjectMapper(new YAMLFactory());
        ObjectMapper json = new ObjectMapper();

        RuleSet ruleSet = yaml.readValue(rulesFile, RuleSet.class);
        SystemContext context = json.readValue(scenarioFile, SystemContext.class);

        RuleEngine engine = new RuleEngine();
        List<Recommendation> recs = engine.evaluate(ruleSet, context);

        System.out.println("=== Orbital Resilience Engine ===");
        System.out.println("Scenario: " + scenarioFile.getName());
        System.out.println("Rules loaded: " + ruleSet.getRules().size());
        System.out.println();

        for (Recommendation r : recs) {
            System.out.println("- [" + r.severity + "] " + r.message);
            if (r.controls != null && !r.controls.isEmpty()) {
                System.out.println("  Controls: " + String.join(", ", r.controls));
            }
            if (r.actions != null && !r.actions.isEmpty()) {
                System.out.println("  Actions: " + String.join(", ", r.actions));
            }
        }

        int score = recs.stream().mapToInt(Recommendation::riskDelta).sum();
        System.out.println("\nEstimated risk delta (lower is better): " + score);
    }
}

