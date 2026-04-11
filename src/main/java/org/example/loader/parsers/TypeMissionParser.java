package org.example.loader.parsers;

import org.example.builder.DefaultMissionBuilder;
import org.example.builder.MissionDirector;
import org.example.model.*;
import org.example.model.enums.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TypeMissionParser {
    public Mission parse(File file) throws IOException {
        MissionDirector d = new MissionDirector(new DefaultMissionBuilder());
        Curse curse = new Curse();
        EnemyActivity enemy = new EnemyActivity();
        CivilianImpact civil = new CivilianImpact();
        for (String raw : Files.readAllLines(file.toPath())) {
            String line = raw.trim();
            if (line.isEmpty()) continue;
            String[] p = line.split("\\|");
            switch (p[0]) {
                case "MISSION_CREATED" -> { d.missionId(p[1]); d.date(LocalDate.parse(p[2])); d.location(p[3]); }
                case "CURSE_DETECTED" -> { curse.setName(p[1]); curse.setThreatLevel(ThreatLevel.fromString(p[2])); d.curse(curse); }
                case "SORCERER_ASSIGNED" -> { Sorcerer s = new Sorcerer(); s.setName(p[1]); s.setRank(SorcererRank.fromString(p[2])); d.addSorcerer(s); }
                case "TECHNIQUE_USED" -> { Technique t = new Technique(); t.setName(p[1]); t.setType(TechniqueType.fromString(p[2])); t.setOwner(p[3]); t.setDamage(Long.parseLong(p[4])); d.addTechnique(t); }
                case "TIMELINE_EVENT" -> { TimelineEvent ev = new TimelineEvent(); ev.setTimestamp(LocalDateTime.parse(p[1])); ev.setType(p[2]); ev.setDescription(p[3]); d.addTimelineEvent(ev); }
                case "ENEMY_ACTION" -> { enemy.getAttackPatterns().add(p[2]); enemy.setBehaviorType(p[1]); d.enemyActivity(enemy); }
                case "CIVILIAN_IMPACT" -> {
                    for (int i=1;i<p.length;i++) {
                        String[] kv = p[i].split("=",2);
                        if (kv.length != 2) continue;
                        if ("evacuated".equals(kv[0])) civil.setEvacuated(Integer.parseInt(kv[1]));
                        if ("injured".equals(kv[0])) civil.setInjured(Integer.parseInt(kv[1]));
                        if ("missing".equals(kv[0])) civil.setMissing(Integer.parseInt(kv[1]));
                    }
                    d.civilianImpact(civil);
                }
                case "MISSION_RESULT" -> { d.outcome(MissionOutcome.fromString(p[1])); if (p.length>2 && p[2].startsWith("damageCost=")) d.damageCost(Long.parseLong(p[2].substring("damageCost=".length()))); }
            }
        }
        return d.build();
    }
}
