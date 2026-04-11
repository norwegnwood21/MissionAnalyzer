package org.example.loader.parsers;

import org.example.builder.DefaultMissionBuilder;
import org.example.builder.MissionDirector;
import org.example.model.*;
import org.example.model.enums.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

public class TxtMissionParser {
    public Mission parse(File file) throws IOException {
        MissionDirector d = new MissionDirector(new DefaultMissionBuilder());
        List<String> lines = Files.readAllLines(file.toPath());
        String section = "";
        Sorcerer currentSorcerer = null;
        Technique currentTechnique = null;
        EnvironmentConditions env = null;
        Curse curse = null;

        for (String raw : lines) {
            String line = raw.trim();
            if (line.isEmpty()) continue;
            if (line.startsWith("[") && line.endsWith("]")) {
                if (currentSorcerer != null) { d.addSorcerer(currentSorcerer); currentSorcerer = null; }
                if (currentTechnique != null) { d.addTechnique(currentTechnique); currentTechnique = null; }
                if (env != null && "[ENVIRONMENT]".equals(section)) { d.environmentConditions(env); env = null; }
                if (curse != null && "[CURSE]".equals(section)) { d.curse(curse); }

                section = line;
                if ("[SORCERER]".equals(section)) currentSorcerer = new Sorcerer();
                if ("[TECHNIQUE]".equals(section)) currentTechnique = new Technique();
                if ("[ENVIRONMENT]".equals(section)) env = new EnvironmentConditions();
                if ("[CURSE]".equals(section)) curse = new Curse();
                continue;
            }
            String[] parts = line.split("=", 2);
            if (parts.length != 2) continue;
            String key = parts[0].trim(); String value = parts[1].trim();
            switch (section) {
                case "[MISSION]" -> {
                    if ("missionId".equals(key)) d.missionId(value);
                    else if ("date".equals(key)) d.date(LocalDate.parse(value));
                    else if ("location".equals(key)) d.location(value);
                    else if ("outcome".equals(key)) d.outcome(MissionOutcome.fromString(value));
                    else if ("damageCost".equals(key)) d.damageCost(Long.parseLong(value));
                }
                case "[CURSE]" -> {
                    if ("name".equals(key)) curse.setName(value);
                    else if ("threatLevel".equals(key)) curse.setThreatLevel(ThreatLevel.fromString(value));
                }
                case "[SORCERER]" -> {
                    if ("name".equals(key)) currentSorcerer.setName(value);
                    else if ("rank".equals(key)) currentSorcerer.setRank(SorcererRank.fromString(value));
                }
                case "[TECHNIQUE]" -> {
                    if ("name".equals(key)) currentTechnique.setName(value);
                    else if ("type".equals(key)) currentTechnique.setType(TechniqueType.fromString(value));
                    else if ("owner".equals(key)) currentTechnique.setOwner(value);
                    else if ("damage".equals(key)) currentTechnique.setDamage(Long.parseLong(value));
                }
                case "[ENVIRONMENT]" -> {
                    if ("weather".equals(key)) env.setWeather(value);
                    else if ("timeOfDay".equals(key)) env.setTimeOfDay(value);
                    else if ("visibility".equals(key)) env.setVisibility(Visibility.fromString(value));
                    else if ("cursedEnergyDensity".equals(key)) env.setCursedEnergyDensity(Integer.parseInt(value));
                }
            }
        }
        if (currentSorcerer != null) d.addSorcerer(currentSorcerer);
        if (currentTechnique != null) d.addTechnique(currentTechnique);
        if (env != null) d.environmentConditions(env);
        if (curse != null) d.curse(curse);
        return d.build();
    }
}
