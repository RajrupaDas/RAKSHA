package com.spaceresilience;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        System.out.println("Space Resilience System Started");

        RiskModule riskModule = new RiskModule();
        HealthMonitor healthMonitor = new HealthMonitor();
        ResultsLogger resultsLogger = new ResultsLogger();

        try {
            // Ensure results folder exists
            Files.createDirectories(Paths.get("results"));

            Path scenariosDir = Paths.get("src/main/resources/scenarios");
            if (!Files.exists(scenariosDir)) {
                System.out.println("No scenarios found at: " + scenariosDir.toAbsolutePath());
                return;
            }

            // Get all scenario files
            Stream<Path> scenarioPaths = Files.list(scenariosDir).filter(Files::isRegularFile)
                                              .filter(path -> path.toString().endsWith(".json"));
            Path[] scenarios = scenarioPaths.toArray(Path[]::new);

            System.out.println("Found " + scenarios.length + " scenarios.");

            // Infinite loop to keep cycling through scenarios
            while (true) {
                for (Path scenarioPath : scenarios) {
                    String scenarioName = scenarioPath.getFileName().toString();
                    System.out.println("Running scenario: " + scenarioName);

                    // Update health metrics
                    int cpuLoad = healthMonitor.getCpuLoad();
                    int memoryLoad = healthMonitor.getMemoryLoad();
                    int networkLoad = healthMonitor.getNetworkLoad();

                    // Update risk based on metrics
                    int riskValue = riskModule.calculateRisk(cpuLoad, memoryLoad, networkLoad);

                    // Trigger recovery if needed
                    boolean recoveryTriggered = riskModule.checkAndRecover(riskValue);

                    // Log results
                    resultsLogger.logResult(scenarioName, cpuLoad, memoryLoad, networkLoad, riskValue, recoveryTriggered);

                    try {
                        Thread.sleep(1000); // 1 sec delay between scenarios
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

