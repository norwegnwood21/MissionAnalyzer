package org.example.report;
import org.example.model.Mission;
public class ShortMissionReportStrategy implements MissionReportStrategy {
    @Override
    public String generate(Mission mission) {
        return "Миссия  " + mission.getMissionId() + " | " + mission.getDate() + " | " + mission.getOutcome() + " | " + mission.getLocation();
    }
}
