package org.example.loader.parsers;

import org.example.builder.DefaultMissionBuilder;
import org.example.builder.MissionDirector;
import org.example.loader.parsers.patterns_raw.*;
import org.example.model.*;
import org.example.model.enums.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class RawMissionMapper {
    private RawMissionMapper() {}

    public static Mission map(MissionRaw raw) {
        MissionDirector d = new MissionDirector(new DefaultMissionBuilder())
                .missionId(raw.missionId)
                .date(parseDate(raw.date))
                .location(raw.location)
                .outcome(MissionOutcome.fromString(raw.outcome))
                .damageCost(raw.damageCost);

        if (raw.curse != null) {
            Curse curse = new Curse();
            curse.setName(raw.curse.name);
            curse.setThreatLevel(ThreatLevel.fromString(raw.curse.threatLevel));
            d.curse(curse);
        }
        if (raw.sorcerers != null) for (SorcererRaw sr : raw.sorcerers) {
            Sorcerer s = new Sorcerer();
            s.setName(sr.name);
            s.setRank(SorcererRank.fromString(sr.rank));
            d.addSorcerer(s);
        }
        if (raw.techniques != null) for (TechniqueRaw tr : raw.techniques) {
            Technique t = new Technique();
            t.setName(tr.name); t.setType(TechniqueType.fromString(tr.type)); t.setOwner(tr.owner); t.setDamage(tr.damage);
            d.addTechnique(t);
        }
        if (raw.economicAssessment != null) {
            EconomicAssessment e = new EconomicAssessment();
            e.setTotalDamageCost(raw.economicAssessment.totalDamageCost);
            e.setInfrastructureDamage(raw.economicAssessment.infrastructureDamage);
            e.setCommercialDamage(raw.economicAssessment.commercialDamage);
            e.setTransportDamage(raw.economicAssessment.transportDamage);
            e.setRecoveryEstimateDays(raw.economicAssessment.recoveryEstimateDays);
            e.setInsuranceCovered(raw.economicAssessment.insuranceCovered);
            d.economicAssessment(e);
        }
        if (raw.civilianImpact != null) {
            CivilianImpact c = new CivilianImpact();
            c.setEvacuated(raw.civilianImpact.evacuated);
            c.setInjured(raw.civilianImpact.injured);
            c.setMissing(raw.civilianImpact.missing);
            c.setPublicExposureRisk(PublicExposureRisk.fromString(raw.civilianImpact.publicExposureRisk));
            d.civilianImpact(c);
        }
        if (raw.enemyActivity != null) {
            EnemyActivity ea = new EnemyActivity();
            ea.setBehaviorType(raw.enemyActivity.behaviorType);
            if (raw.enemyActivity.targetPriority != null) ea.getTargetPriority().addAll(raw.enemyActivity.targetPriority);
            if (raw.enemyActivity.attackPatterns != null) ea.getAttackPatterns().addAll(raw.enemyActivity.attackPatterns);
            ea.setMobility(Mobility.fromString(raw.enemyActivity.mobility));
            ea.setEscalationRisk(EscalationRisk.fromString(raw.enemyActivity.escalationRisk));
            if (raw.enemyActivity.countermeasuresUsed != null) ea.getCountermeasuresUsed().addAll(raw.enemyActivity.countermeasuresUsed);
            d.enemyActivity(ea);
        }
        if (raw.environmentConditions != null) {
            EnvironmentConditions env = new EnvironmentConditions();
            env.setWeather(raw.environmentConditions.weather);
            env.setTimeOfDay(raw.environmentConditions.timeOfDay);
            env.setVisibility(Visibility.fromString(raw.environmentConditions.visibility));
            env.setCursedEnergyDensity(raw.environmentConditions.cursedEnergyDensity);
            d.environmentConditions(env);
        }
        if (raw.operationTimeline != null) for (TimelineEventRaw tvr : raw.operationTimeline) {
            TimelineEvent te = new TimelineEvent();
            te.setTimestamp(parseDateTime(tvr.timestamp)); te.setType(tvr.type); te.setDescription(tvr.description); d.addTimelineEvent(te);
        }
        if (raw.operationTags != null) raw.operationTags.forEach(d::addOperationTag);
        if (raw.supportUnits != null) raw.supportUnits.forEach(d::addSupportUnit);
        if (raw.recommendations != null) raw.recommendations.forEach(d::addRecommendation);
        if (raw.notes != null) d.notes(raw.notes);
        if (raw.artifactsRecovered != null) raw.artifactsRecovered.forEach(d::addArtifactRecovered);
        if (raw.evacuationZones != null) raw.evacuationZones.forEach(d::addEvacuationZone);
        if (raw.statusEffects != null) raw.statusEffects.forEach(d::addStatusEffect);
        return d.build();
    }

    public static LocalDate parseDate(String v) { return v == null || v.isBlank() ? null : LocalDate.parse(v.trim()); }
    public static LocalDateTime parseDateTime(String v) { return v == null || v.isBlank() ? null : LocalDateTime.parse(v.trim()); }
}
