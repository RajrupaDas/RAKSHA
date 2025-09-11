package com.spaceresilience;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ResultsLogger {
    private PrintWriter writer;

    public ResultsLogger() {
        try {
            // Open CSV in append mode
            writer = new PrintWriter(new FileWriter("results/results.csv", true));

            // Write header if file is empty
            writer.printf("Timestamp,Scenario,CPU,Memory,Network,Risk,Recovery%n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logResult(String scenario, int cpu, int memory, int network, int risk, boolean recovery) {
        if (writer == null) return;
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        writer.printf("%s,%s,%d,%d,%d,%d,%b%n", timestamp, scenario, cpu, memory, network, risk, recovery);
        writer.flush();
    }
}



