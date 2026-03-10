package org.example.parser;

import org.example.model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class TextMissionParser {


    public Mission parseFile(File file) throws IOException {
        Mission mission = new Mission();
        Path path = file.toPath();

        List<String> lines = Files.readAllLines(path);

        Sorcerer currentSorcerer = null;
        Technique currentTechnique = null;

        for (String line : lines) {
            line = line.trim();


            if (line.isEmpty()) {
                continue;
            }


            String[] parts = line.split(":", 2);
            if (parts.length < 2) {
                continue; // Пропускаем строки без двоеточия
            }

            String key = parts[0].trim();
            String value = parts[1].trim();


            if (key.equals("missionId")) {
                mission.setMissionId(value);

            } else if (key.equals("date")) {
                mission.setDate(value);

            } else if (key.equals("location")) {
                mission.setLocation(value);

            } else if (key.equals("outcome")) {
                mission.setOutcome(value);

            } else if (key.equals("damageCost")) {
                mission.setDamageCost(Integer.parseInt(value));

            } else if (key.equals("curse.name")) {
                if (mission.getCurse() == null) {
                    mission.setCurse(new Curse());
                }
                mission.getCurse().setName(value);

            } else if (key.equals("curse.threatLevel")) {
                if (mission.getCurse() == null) {
                    mission.setCurse(new Curse());
                }
                mission.getCurse().setThreatLevel(value);

            } else if (key.startsWith("sorcerer[") && key.endsWith("].name")) {

                currentSorcerer = new Sorcerer();
                currentSorcerer.setName(value);
                mission.getSorcerers().add(currentSorcerer);

            } else if (key.startsWith("sorcerer[") && key.endsWith("].rank")) {

                if (!mission.getSorcerers().isEmpty()) {
                    Sorcerer last = mission.getSorcerers().get(mission.getSorcerers().size() - 1);
                    last.setRank(value);
                }

            } else if (key.startsWith("technique[") && key.endsWith("].name")) {

                currentTechnique = new Technique();
                currentTechnique.setName(value);
                mission.getTechniques().add(currentTechnique);

            } else if (key.startsWith("technique[") && key.endsWith("].type")) {
                if (!mission.getTechniques().isEmpty()) {
                    Technique last = mission.getTechniques().get(mission.getTechniques().size() - 1);
                    last.setType(value);
                }

            } else if (key.startsWith("technique[") && key.endsWith("].owner")) {
                if (!mission.getTechniques().isEmpty()) {
                    Technique last = mission.getTechniques().get(mission.getTechniques().size() - 1);
                    last.setOwner(value);
                }

            } else if (key.startsWith("technique[") && key.endsWith("].damage")) {
                if (!mission.getTechniques().isEmpty()) {
                    Technique last = mission.getTechniques().get(mission.getTechniques().size() - 1);
                    last.setDamage(Integer.parseInt(value));
                }

            } else if (key.equals("note")) {
                mission.setNote(value);

            } else if (key.equals("comment")) {
                mission.setComment(value);
            }
        }

        return mission;
    }


    public boolean canParse(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".txt") || name.endsWith(".text");
    }
}