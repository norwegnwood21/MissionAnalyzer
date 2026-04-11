package org.example.builder;

import org.example.model.*;
import org.example.model.enums.MissionOutcome;

import java.time.LocalDate;


public class MissionDirector {
    private final MissionBuilder builder;

    public MissionDirector(MissionBuilder builder) {
        this.builder = builder;
    }

    public MissionDirector missionId(String value) { builder.missionId(value); return this; }
    public MissionDirector date(LocalDate value) { builder.date(value); return this; }
    public MissionDirector location(String value) { builder.location(value); return this; }
    public MissionDirector outcome(MissionOutcome value) { builder.outcome(value); return this; }
    public MissionDirector damageCost(Long value) { builder.damageCost(value); return this; }
    public MissionDirector curse(Curse value) { builder.curse(value); return this; }
    public MissionDirector addSorcerer(Sorcerer value) { builder.addSorcerer(value); return this; }
    public MissionDirector addTechnique(Technique value) { builder.addTechnique(value); return this; }
    public MissionDirector economicAssessment(EconomicAssessment value) { builder.economicAssessment(value); return this; }
    public MissionDirector civilianImpact(CivilianImpact value) { builder.civilianImpact(value); return this; }
    public MissionDirector enemyActivity(EnemyActivity value) { builder.enemyActivity(value); return this; }
    public MissionDirector environmentConditions(EnvironmentConditions value) { builder.environmentConditions(value); return this; }
    public MissionDirector addTimelineEvent(TimelineEvent value) { builder.addTimelineEvent(value); return this; }
    public MissionDirector addOperationTag(String value) { builder.addOperationTag(value); return this; }
    public MissionDirector addSupportUnit(String value) { builder.addSupportUnit(value); return this; }
    public MissionDirector addRecommendation(String value) { builder.addRecommendation(value); return this; }
    public MissionDirector notes(String value) { builder.notes(value); return this; }
    public MissionDirector addArtifactRecovered(String value) { builder.addArtifactRecovered(value); return this; }
    public MissionDirector addEvacuationZone(String value) { builder.addEvacuationZone(value); return this; }
    public MissionDirector addStatusEffect(String value) { builder.addStatusEffect(value); return this; }

    public Mission build() { return builder.build(); }
}
