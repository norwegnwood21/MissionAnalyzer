package org.example.service;

import jakarta.transaction.Transactional;
import org.example.model.Mission;
import org.example.repository.MissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MissionArchiveService {
    private final MissionRepository missionRepository;

    public MissionArchiveService(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    @Transactional
    public Mission save(Mission mission) {
        if (missionRepository.findByMissionId(mission.getMissionId()).isPresent()) {
            throw new IllegalArgumentException("Миссия с данным id уже присутствует в архиве");
        }
        return missionRepository.save(mission);
    }

    @Transactional
    public List<Mission> findAllMissions() {
        return missionRepository.findAll();
    }

    @Transactional
    public Mission findDetailedByMissionId(String missionId) {
        Mission mission = missionRepository.findByMissionId(missionId)
                .orElseThrow(() -> new IllegalArgumentException("Миссия не найдена: " + missionId));

        if (mission.getCurse() != null) {
            mission.getCurse().getName();
        }

        mission.getSorcerers().size();
        mission.getTechniques().size();
        mission.getOperationTimeline().size();
        mission.getOperationTags().size();
        mission.getSupportUnits().size();
        mission.getRecommendations().size();
        mission.getArtifactsRecovered().size();
        mission.getEvacuationZones().size();
        mission.getStatusEffects().size();

        if (mission.getEconomicAssessment() != null) {
            mission.getEconomicAssessment().getTotalDamageCost();
        }

        if (mission.getCivilianImpact() != null) {
            mission.getCivilianImpact().getEvacuated();
        }

        if (mission.getEnemyActivity() != null) {
            mission.getEnemyActivity().getBehaviorType();
            mission.getEnemyActivity().getTargetPriority().size();
            mission.getEnemyActivity().getAttackPatterns().size();
            mission.getEnemyActivity().getCountermeasuresUsed().size();
        }

        if (mission.getEnvironmentConditions() != null) {
            mission.getEnvironmentConditions().getWeather();
        }

        return mission;
    }

    @Transactional
    public void deleteByMissionId(String missionId) {
        Mission mission = missionRepository.findByMissionId(missionId)
                .orElseThrow(() -> new IllegalArgumentException("Миссия не найдена: " + missionId));
        missionRepository.delete(mission);
    }
}