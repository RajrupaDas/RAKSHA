package com.spaceresilience.models;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;

public class Scenario {
    public String name;
    public Risk risk;
    public Recovery recovery;

    public static class Risk {
        public String type;
        public String severity;
        public double likelihood;
        public String impact;
    }

    public static class Recovery {
        public String strategy;
        public int mttd;
        public int mttr;
    }

    public static Scenario fromJson(java.io.File file) {
        try (FileReader reader = new FileReader(file)) {
            return new Gson().fromJson(reader, Scenario.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

