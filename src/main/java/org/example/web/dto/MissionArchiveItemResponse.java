package org.example.web.dto;

public record MissionArchiveItemResponse(
        String missionId,
        String date,
        String location,
        String outcome
) {
}