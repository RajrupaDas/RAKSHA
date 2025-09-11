package com.spaceresilience;

import java.util.Random;

public class HealthMonitor {
    private Random random = new Random();

    public int getCpuLoad() {
        return random.nextInt(100); // simulate CPU load 0-99%
    }

    public int getMemoryLoad() {
        return random.nextInt(100); // simulate Memory usage
    }

    public int getNetworkLoad() {
        return random.nextInt(100); // simulate Network usage
    }
}

