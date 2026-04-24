package org.example.service;

import org.example.model.Mission;
import org.example.report.MissionReportStrategy;
import org.example.report.ShortMissionReportStrategy;
import org.example.report.SummaryMissionReportStrategy;
import org.springframework.stereotype.Service;

@Service
public class MissionReportService {

    public String generateReport(Mission mission, String type) {
        MissionReportStrategy strategy = switch ((type == null ? "summary" : type).toLowerCase()) {
            case "short" -> new ShortMissionReportStrategy();
            default -> new SummaryMissionReportStrategy();
        };
        return strategy.generate(mission);
    }
}