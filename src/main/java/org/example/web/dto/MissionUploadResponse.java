package org.example.web.dto;

public record MissionUploadResponse(
        String message,
        String missionId,
        String format
) {
}