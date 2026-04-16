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
import java.util.List;

public class TxtMissionParser {

    public Mission parse(File file) throws IOException {
        MissionDirector d = new MissionDirector(new DefaultMissionBuilder());
        List<String> lines = Files.readAllLines(file.toPath());

        String section = "";
        String notesContent = null;


        Sorcerer currentSorcerer = null;
        Technique currentTechnique = null;
        EnvironmentConditions env = null;
        Curse curse = null;
        CivilianImpact civilianImpact = null;
        EnemyActivity enemyActivity = null;
        EconomicAssessment economicAssessment = null;
        TimelineEvent currentTimelineEvent = null;

        for (String raw : lines) {
            String line = raw.trim();
            if (line.isEmpty()) continue;


            if (line.startsWith("[") && line.endsWith("]")) {
                if (currentSorcerer != null) {
                    d.addSorcerer(currentSorcerer);
                    currentSorcerer = null;
                }
                if (currentTechnique != null) {
                    d.addTechnique(currentTechnique);
                    currentTechnique = null;
                }
                if (env != null && "[ENVIRONMENT]".equals(section)) {
                    d.environmentConditions(env);
                    env = null;
                }
                if (curse != null && "[CURSE]".equals(section)) {
                    d.curse(curse);
                    curse = null;
                }
                if (civilianImpact != null && "[CIVILIAN_IMPACT]".equals(section)) {
                    d.civilianImpact(civilianImpact);
                    civilianImpact = null;
                }
                if (enemyActivity != null && "[ENEMY_ACTIVITY]".equals(section)) {
                    d.enemyActivity(enemyActivity);
                    enemyActivity = null;
                }
                if (economicAssessment != null && "[ECONOMIC_ASSESSMENT]".equals(section)) {
                    d.economicAssessment(economicAssessment);
                    economicAssessment = null;
                }
                if (currentTimelineEvent != null && "[TIMELINE]".equals(section)) {
                    d.addTimelineEvent(currentTimelineEvent);
                    currentTimelineEvent = null;
                }

                if (notesContent != null && "[NOTES]".equals(section)) {
                    d.notes(notesContent);
                    notesContent = null;
                }

                section = line;


                if ("[SORCERER]".equals(section)) currentSorcerer = new Sorcerer();
                if ("[TECHNIQUE]".equals(section)) currentTechnique = new Technique();
                if ("[ENVIRONMENT]".equals(section)) env = new EnvironmentConditions();
                if ("[CURSE]".equals(section)) curse = new Curse();
                if ("[CIVILIAN_IMPACT]".equals(section)) civilianImpact = new CivilianImpact();
                if ("[ENEMY_ACTIVITY]".equals(section)) enemyActivity = new EnemyActivity();
                if ("[ECONOMIC_ASSESSMENT]".equals(section)) economicAssessment = new EconomicAssessment();
                if ("[TIMELINE]".equals(section)) currentTimelineEvent = new TimelineEvent();

                if ("[NOTES]".equals(section)) notesContent = "";
                continue;
            }


            if ("[NOTES]".equals(section) && notesContent != null) {
                notesContent += (notesContent.isEmpty() ? "" : "\n") + line;
                continue;
            }


            String[] parts = line.split("=", 2);
            if (parts.length != 2) continue;
            String key = parts[0].trim();
            String value = parts[1].trim();

            switch (section) {
                case "[MISSION]" -> handleMissionSection(d, key, value);
                case "[CURSE]" -> handleCurseSection(curse, key, value);
                case "[SORCERER]" -> handleSorcererSection(currentSorcerer, key, value);
                case "[TECHNIQUE]" -> handleTechniqueSection(currentTechnique, key, value);
                case "[ENVIRONMENT]" -> handleEnvironmentSection(env, key, value);
                case "[CIVILIAN_IMPACT]" -> handleCivilianImpactSection(civilianImpact, key, value);
                case "[ENEMY_ACTIVITY]" -> handleEnemyActivitySection(enemyActivity, key, value);
                case "[ECONOMIC_ASSESSMENT]" -> handleEconomicAssessmentSection(economicAssessment, key, value);
                case "[TIMELINE]" -> handleTimelineSection(currentTimelineEvent, key, value);
            }
        }


        if (currentSorcerer != null) d.addSorcerer(currentSorcerer);
        if (currentTechnique != null) d.addTechnique(currentTechnique);
        if (env != null) d.environmentConditions(env);
        if (curse != null) d.curse(curse);
        if (civilianImpact != null) d.civilianImpact(civilianImpact);
        if (enemyActivity != null) d.enemyActivity(enemyActivity);
        if (economicAssessment != null) d.economicAssessment(economicAssessment);
        if (currentTimelineEvent != null) d.addTimelineEvent(currentTimelineEvent);

        if (notesContent != null) d.notes(notesContent);

        return d.build();
    }



    private void handleMissionSection(MissionDirector d, String key, String value) {
        switch (key) {
            case "missionId" -> d.missionId(value);
            case "date" -> d.date(LocalDate.parse(value));
            case "location" -> d.location(value);
            case "outcome" -> d.outcome(MissionOutcome.fromString(value));
            case "damageCost" -> d.damageCost(Long.parseLong(value));
            case "notes" -> d.notes(value);
            case "operationTags" -> {
                for (String tag : value.split(",")) d.addOperationTag(tag.trim());
            }
            case "supportUnits" -> {
                for (String unit : value.split(",")) d.addSupportUnit(unit.trim());
            }
            case "recommendations" -> {
                for (String rec : value.split(",")) d.addRecommendation(rec.trim());
            }
            case "artifactsRecovered" -> {
                for (String art : value.split(",")) d.addArtifactRecovered(art.trim());
            }
            case "evacuationZones" -> {
                for (String zone : value.split(",")) d.addEvacuationZone(zone.trim());
            }
            case "statusEffects" -> {
                for (String effect : value.split(",")) d.addStatusEffect(effect.trim());
            }
        }
    }

    private void handleCurseSection(Curse curse, String key, String value) {
        switch (key) {
            case "name" -> curse.setName(value);
            case "threatLevel" -> curse.setThreatLevel(ThreatLevel.fromString(value));
        }
    }

    private void handleSorcererSection(Sorcerer sorcerer, String key, String value) {
        switch (key) {
            case "name" -> sorcerer.setName(value);
            case "rank" -> sorcerer.setRank(SorcererRank.fromString(value));
        }
    }

    private void handleTechniqueSection(Technique technique, String key, String value) {
        switch (key) {
            case "name" -> technique.setName(value);
            case "type" -> technique.setType(TechniqueType.fromString(value));
            case "owner" -> technique.setOwner(value);
            case "damage" -> technique.setDamage(Long.parseLong(value));
        }
    }

    private void handleEnvironmentSection(EnvironmentConditions env, String key, String value) {
        switch (key) {
            case "weather" -> env.setWeather(value);
            case "timeOfDay" -> env.setTimeOfDay(value);
            case "visibility" -> env.setVisibility(Visibility.fromString(value));
            case "cursedEnergyDensity" -> env.setCursedEnergyDensity(Integer.parseInt(value));
        }
    }

    private void handleCivilianImpactSection(CivilianImpact ci, String key, String value) {
        switch (key) {
            case "evacuated" -> ci.setEvacuated(Integer.parseInt(value));
            case "injured" -> ci.setInjured(Integer.parseInt(value));
            case "missing" -> ci.setMissing(Integer.parseInt(value));
            case "publicExposureRisk" -> ci.setPublicExposureRisk(PublicExposureRisk.fromString(value));
        }
    }

    private void handleEnemyActivitySection(EnemyActivity ea, String key, String value) {
        switch (key) {
            case "behaviorType" -> ea.setBehaviorType(value);
            case "mobility" -> ea.setMobility(Mobility.fromString(value));
            case "escalationRisk" -> ea.setEscalationRisk(EscalationRisk.fromString(value));
            case "targetPriority" -> {
                for (String target : value.split(",")) ea.getTargetPriority().add(target.trim());
            }
            case "attackPatterns" -> {
                for (String pattern : value.split("\\|")) ea.getAttackPatterns().add(pattern.trim());
            }
            case "countermeasuresUsed" -> {
                for (String measure : value.split(",")) ea.getCountermeasuresUsed().add(measure.trim());
            }
        }
    }

    private void handleEconomicAssessmentSection(EconomicAssessment ea, String key, String value) {
        switch (key) {
            case "totalDamageCost" -> ea.setTotalDamageCost(Long.parseLong(value));
            case "infrastructureDamage" -> ea.setInfrastructureDamage(Long.parseLong(value));
            case "commercialDamage" -> ea.setCommercialDamage(Long.parseLong(value));
            case "transportDamage" -> ea.setTransportDamage(Long.parseLong(value));
            case "recoveryEstimateDays" -> ea.setRecoveryEstimateDays(Integer.parseInt(value));
            case "insuranceCovered" -> ea.setInsuranceCovered(Boolean.parseBoolean(value));
        }
    }

    private void handleTimelineSection(TimelineEvent event, String key, String value) {
        switch (key) {
            case "timestamp" -> event.setTimestamp(LocalDateTime.parse(value));
            case "type" -> event.setType(value);
            case "description" -> event.setDescription(value);
        }
    }
}