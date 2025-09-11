package com.spaceresilience;

public class RiskModule {
    public int calculateRisk(int cpu, int memory, int network) {
        // Simple risk calculation: weighted sum
        return (cpu + memory + network) / 3;
    }

    public boolean checkAndRecover(int riskValue) {
        // Simple recovery trigger if risk > threshold
        if (riskValue > 70) {
            System.out.println("High risk detected! Triggering recovery...");
            return true;
        }
        return false;
    }
}

