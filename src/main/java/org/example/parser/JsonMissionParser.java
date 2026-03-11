package org.example.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Mission;
import org.example.model.Sorcerer;
import org.example.model.Technique;

import java.io.File;
import java.io.IOException;

public class JsonMissionParser {

    private final ObjectMapper objectMapper;

    public JsonMissionParser() {
        this.objectMapper = new ObjectMapper();
    }

    public Mission parseFile(File file) throws IOException {
        Mission mission = objectMapper.readValue(file, Mission.class);
        linkTechniqueOwners(mission);
        return mission;
    }

    public boolean canParse(File file) {
        return file.getName().toLowerCase().endsWith(".json");
    }

    private void linkTechniqueOwners(Mission mission) {
        if (mission == null || mission.getTechniques() == null || mission.getSorcerers() == null) {
            return;
        }

        for (Technique technique : mission.getTechniques()) {
            if (technique.getOwner() == null || technique.getOwner().getName() == null) {
                continue;
            }

            String ownerName = technique.getOwner().getName();
            Sorcerer foundOwner = findSorcererByName(mission, ownerName);

            if (foundOwner != null) {
                technique.setOwner(foundOwner);
            } else {
                mission.getSorcerers().add(technique.getOwner());
            }
        }
    }

    private Sorcerer findSorcererByName(Mission mission, String name) {
        for (Sorcerer sorcerer : mission.getSorcerers()) {
            if (sorcerer.getName() != null && sorcerer.getName().equals(name)) {
                return sorcerer;
            }
        }
        return null;
    }
}