package com.spaceresilience.mbse;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

public class HealthModule implements Module {

    private OperatingSystemMXBean osBean;

    public HealthModule() {
        osBean = ManagementFactory.getOperatingSystemMXBean();
    }

    @Override
    public void start() {
        System.out.println("Health Monitoring Started");
    }

    @Override
    public void stop() {
        System.out.println("Health Monitoring Stopped");
    }

    @Override
    public String getStatus() {
        double load = osBean.getSystemLoadAverage();
        return "CPU Load: " + load;
    }
}

