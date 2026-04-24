package org.example.web.dto;

import java.util.List;

public record MissionDetailsResponse(
        String missionId,
        String date,
        String location,
        String outcome,
        Long damageCost,
        String curseName,
        String curseThreatLevel,
        List<String> sorcerers,
        List<String> techniques,
        String notes
) {
}