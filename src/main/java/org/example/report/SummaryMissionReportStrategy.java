package org.example.report;

import org.example.model.*;

public class SummaryMissionReportStrategy implements MissionReportStrategy {
    @Override
    public String generate(Mission mission) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== СВОДКА ПО МИССИИ ===\n");
        sb.append("ID: ").append(mission.getMissionId()).append("\n");
        sb.append("Дата: ").append(mission.getDate()).append("\n");
        sb.append("Локация: ").append(mission.getLocation()).append("\n");
        sb.append("Результат: ").append(mission.getOutcome()).append("\n");
        if (mission.getDamageCost() != null) sb.append("Ущерб: ").append(mission.getDamageCost()).append("\n");
        sb.append("Проклятие: ").append(mission.getCurse().getName()).append(" / ").append(mission.getCurse().getThreatLevel()).append("\n");

        if (!mission.getSorcerers().isEmpty()) {
            sb.append("Участники:\n");
            for (Sorcerer s : mission.getSorcerers()) sb.append("  - ").append(s.getName()).append(" (").append(s.getRank()).append(")\n");
        }

        if (!mission.getTechniques().isEmpty()) {
            sb.append("Техники:\n");
            for (Technique t : mission.getTechniques()) sb.append("  - ").append(t.getName()).append(", ").append(t.getType()).append(", владелец: ").append(t.getOwner()).append(t.getDamage()!=null?", урон: "+t.getDamage():"").append("\n");
        }

        if (mission.getNotes() != null) sb.append("Примечание: ").append(mission.getNotes()).append("\n");
        return sb.toString();
    }
}