package com.spaceresilience.mbse;

import java.util.HashMap;
import java.util.Map;

public class RiskModule implements Module {

    private Map<String, Integer> riskLevels = new HashMap<>();

    public RiskModule() {
        // default risk ratings
        riskLevels.put("CPU", 0);
        riskLevels.put("Memory", 0);
        riskLevels.put("Network", 0);
        riskLevels.put("Packets", 0);
    }

    public void updateRisk(String component, int level) {
        riskLevels.put(component, level);
        System.out.println("Risk updated: " + component + " -> " + level);
    }

    @Override
    public void start() {
        System.out.println("Risk Module Started");
    }

    @Override
    public void stop() {
        System.out.println("Risk Module Stopped");
    }

    @Override
    public String getStatus() {
        return riskLevels.toString();
    }
}

