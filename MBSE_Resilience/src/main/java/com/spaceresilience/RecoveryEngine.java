package com.spaceresilience;

import com.spaceresilience.models.Scenario;

public class RecoveryEngine {
    public String recover(Scenario scenario) {
        return "Recovered in " + scenario.recovery.mttr + "s using " + scenario.recovery.strategy;
    }
}

