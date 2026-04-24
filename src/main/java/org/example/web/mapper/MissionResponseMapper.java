package org.example.web.mapper;

import org.example.model.Mission;
import org.example.web.dto.MissionArchiveItemResponse;
import org.example.web.dto.MissionDetailsResponse;

import java.util.List;

public final class MissionResponseMapper {

    private MissionResponseMapper() {
    }

    public static MissionArchiveItemResponse toArchiveItem(Mission mission) {
        return new MissionArchiveItemResponse(
                mission.getMissionId(),
                mission.getDate() == null ? null : mission.getDate().toString(),
                mission.getLocation(),
                mission.getOutcome() == null ? null : mission.getOutcome().name()
        );
    }

    public static MissionDetailsResponse toDetails(Mission mission) {
        List<String> sorcerers = mission.getSorcerers().stream()
                .map(s -> s.getName() + " (" + (s.getRank() == null ? null : s.getRank().name()) + ")")
                .toList();

        List<String> techniques = mission.getTechniques().stream()
                .map(t -> t.getName()
                        + " | " + (t.getType() == null ? null : t.getType().name())
                        + " | owner=" + t.getOwner()
                        + " | damage=" + t.getDamage())
                .toList();

        return new MissionDetailsResponse(
                mission.getMissionId(),
                mission.getDate() == null ? null : mission.getDate().toString(),
                mission.getLocation(),
                mission.getOutcome() == null ? null : mission.getOutcome().name(),
                mission.getDamageCost(),
                mission.getCurse() == null ? null : mission.getCurse().getName(),
                mission.getCurse() == null || mission.getCurse().getThreatLevel() == null
                        ? null
                        : mission.getCurse().getThreatLevel().name(),
                sorcerers,
                techniques,
                mission.getNotes()
        );
    }
}