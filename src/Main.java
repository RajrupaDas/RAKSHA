import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.nio.file.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: java Main <rules.yaml> <scenario.json>");
            return;
        }

        File rulesFile = new File(args[0]);
        File scenarioFile = new File(args[1]);

        ObjectMapper yaml = new ObjectMapper(new YAMLFactory());
        ObjectMapper json = new ObjectMapper();

        RuleSet ruleSet = yaml.readValue(rulesFile, RuleSet.class);
        RuleEngine engine = new RuleEngine();

        System.out.println("=== Orbital Resilience Engine (Realtime Mode) ===");
        System.out.println("Watching scenario: " + scenarioFile.getName());
        System.out.println("Rules loaded: " + ruleSet.rules.size());

        // --- Watch file for changes ---
        Path path = scenarioFile.toPath().getParent();
        WatchService watchService = FileSystems.getDefault().newWatchService();
        path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

        // Initial run
        evaluateOnce(json, scenarioFile, engine, ruleSet);

        // Continuous loop
        while (true) {
            WatchKey key = watchService.take();
            for (WatchEvent<?> event : key.pollEvents()) {
                if (event.context().toString().equals(scenarioFile.getName())) {
                    System.out.println("\n[Realtime Update Detected]");
                    evaluateOnce(json, scenarioFile, engine, ruleSet);
                }
            }
            key.reset();
        }
    }

    private static void evaluateOnce(ObjectMapper json, File scenarioFile,
                                     RuleEngine engine, RuleSet ruleSet) throws Exception {
        SystemContext context = json.readValue(scenarioFile, SystemContext.class);
        List<Recommendation> recs = engine.evaluate(ruleSet, context);

        System.out.println("Scenario: " + scenarioFile.getName());
        for (Recommendation r : recs) {
            System.out.println("- [" + r.severity() + "] " + r.message());
            System.out.println("  Controls: " + String.join(", ", r.recommendControls()));
            System.out.println("  Actions: " + String.join(", ", r.actions()));
        }

        int score = engine.calculateResilienceScore(recs);
        System.out.println("Resilience Score (0–100, higher=better): " + score);
    }
}

