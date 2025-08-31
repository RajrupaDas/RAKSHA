package com.spaceresilience;
import com.spaceresilience.mbse.Module;
import com.spaceresilience.mbse.RiskModule;
import com.spaceresilience.mbse.HealthModule;

public class Main {

    public static void main(String[] args) {

        com.spaceresilience.mbse.Module risk = new RiskModule();
        com.spaceresilience.mbse.Module health = new HealthModule();

        // MBSE start
        risk.start();
        health.start();

        // demo updates
        ((RiskModule)risk).updateRisk("CPU", 7);
        System.out.println(health.getStatus());
        System.out.println(risk.getStatus());

        // MBSE stop
        risk.stop();
        health.stop();
    }
}

