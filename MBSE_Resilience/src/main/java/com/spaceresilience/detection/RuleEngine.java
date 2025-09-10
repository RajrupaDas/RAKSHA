package com.spaceresilience.detection;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Iterator;

public class RuleEngine {

    private JsonNode rules;

    public RuleEngine() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        rules = mapper.readTree(new File("config/rules.json"));
    }

    public void checkPacket(String protocol, String payload) {
        Iterator<JsonNode> it = rules.elements();
        while(it.hasNext()) {
            JsonNode rule = it.next();
            if(rule.get("rule").asText().contains(protocol)) {
                System.out.println("ALERT! Packet matches rule: " + rule.get("rule").asText());
            }
        }
    }
}

