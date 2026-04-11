package org.example.loader.parsers;

import org.example.builder.DefaultMissionBuilder;
import org.example.builder.MissionBuilder;
import org.example.model.*;
import org.example.model.enums.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TypeMissionParser {
    public Mission parse(File file) throws IOException {
        MissionBuilder b = new DefaultMissionBuilder();
        Curse curse = new Curse();
        EnemyActivity enemy = new EnemyActivity();
        CivilianImpact civil = new CivilianImpact();
        for (String raw : Files.readAllLines(file.toPath())) {
            String line = raw.trim();
            if (line.isEmpty()) continue;
            String[] p = line.split("\\|");
            switch (p[0]) {
                case "MISSION_CREATED" -> { b.missionId(p[1]); b.date(LocalDate.parse(p[2])); b.location(p[3]); }
                case "CURSE_DETECTED" -> { curse.setName(p[1]); curse.setThreatLevel(ThreatLevel.fromString(p[2])); b.curse(curse); }
                case "SORCERER_ASSIGNED" -> { Sorcerer s = new Sorcerer(); s.setName(p[1]); s.setRank(SorcererRank.fromString(p[2])); b.addSorcerer(s); }
                case "TECHNIQUE_USED" -> { Technique t = new Technique(); t.setName(p[1]); t.setType(TechniqueType.fromString(p[2])); t.setOwner(p[3]); t.setDamage(Long.parseLong(p[4])); b.addTechnique(t); }
                case "TIMELINE_EVENT" -> { TimelineEvent ev = new TimelineEvent(); ev.setTimestamp(LocalDateTime.parse(p[1])); ev.setType(p[2]); ev.setDescription(p[3]); b.addTimelineEvent(ev); }
                case "ENEMY_ACTION" -> { enemy.getAttackPatterns().add(p[2]); enemy.setBehaviorType(p[1]); b.enemyActivity(enemy); }
                case "CIVILIAN_IMPACT" -> {
                    for (int i=1;i<p.length;i++) {
                        String[] kv = p[i].split("=",2);
                        if (kv.length != 2) continue;
                        if ("evacuated".equals(kv[0])) civil.setEvacuated(Integer.parseInt(kv[1]));
                        if ("injured".equals(kv[0])) civil.setInjured(Integer.parseInt(kv[1]));
                        if ("missing".equals(kv[0])) civil.setMissing(Integer.parseInt(kv[1]));
                    }
                    b.civilianImpact(civil);
                }
                case "MISSION_RESULT" -> { b.outcome(MissionOutcome.fromString(p[1])); if (p.length>2 && p[2].startsWith("damageCost=")) b.damageCost(Long.parseLong(p[2].substring("damageCost=".length()))); }
            }
        }
        return b.build();
    }
}
