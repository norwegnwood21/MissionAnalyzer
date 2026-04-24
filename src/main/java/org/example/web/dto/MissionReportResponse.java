package org.example.web.dto;

public record MissionReportResponse(
        String missionId,
        String reportType,
        String content
) {
}