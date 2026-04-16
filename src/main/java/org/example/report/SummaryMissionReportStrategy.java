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
        if (mission.getDamageCost() != null) {
            sb.append("Ущерб: ").append(mission.getDamageCost()).append("\n");
        }


        if (mission.getCurse() != null) {
            sb.append("Проклятие: ").append(mission.getCurse().getName())
                    .append(" / ").append(mission.getCurse().getThreatLevel()).append("\n");
        }


        if (!mission.getSorcerers().isEmpty()) {
            sb.append("Участники:\n");
            for (Sorcerer s : mission.getSorcerers()) {
                sb.append("  - ").append(s.getName()).append(" (").append(s.getRank()).append(")\n");
            }
        }


        if (!mission.getTechniques().isEmpty()) {
            sb.append("Техники:\n");
            for (Technique t : mission.getTechniques()) {
                sb.append("  - ").append(t.getName()).append(", ").append(t.getType())
                        .append(", владелец: ").append(t.getOwner());
                if (t.getDamage() != null) {
                    sb.append(", урон: ").append(t.getDamage());
                }
                sb.append("\n");
            }
        }


        if (mission.getCivilianImpact() != null) {
            CivilianImpact ci = mission.getCivilianImpact();
            sb.append("\n=== ВЛИЯНИЕ НА ГРАЖДАНСКИХ ===\n");
            if (ci.getEvacuated() != null) sb.append("Эвакуировано: ").append(ci.getEvacuated()).append("\n");
            if (ci.getInjured() != null) sb.append("Пострадавшие: ").append(ci.getInjured()).append("\n");
            if (ci.getMissing() != null) sb.append("Пропавшие: ").append(ci.getMissing()).append("\n");
            if (ci.getPublicExposureRisk() != null) {
                sb.append("Риск раскрытия: ").append(ci.getPublicExposureRisk()).append("\n");
            }
        }


        if (mission.getEnemyActivity() != null) {
            EnemyActivity ea = mission.getEnemyActivity();
            sb.append("\n=== АКТИВНОСТЬ ПРОТИВНИКА ===\n");
            if (ea.getBehaviorType() != null && !ea.getBehaviorType().isBlank()) {
                sb.append("Тип поведения: ").append(ea.getBehaviorType()).append("\n");
            }
            if (ea.getMobility() != null) sb.append("Мобильность: ").append(ea.getMobility()).append("\n");
            if (ea.getEscalationRisk() != null) {
                sb.append("Риск эскалации: ").append(ea.getEscalationRisk()).append("\n");
            }
            if (!ea.getAttackPatterns().isEmpty()) {
                sb.append("Паттерны атак: ").append(String.join(", ", ea.getAttackPatterns())).append("\n");
            }
            if (!ea.getTargetPriority().isEmpty()) {
                sb.append("Приоритет целей: ").append(String.join(", ", ea.getTargetPriority())).append("\n");
            }
        }


        if (mission.getEconomicAssessment() != null) {
            EconomicAssessment ea = mission.getEconomicAssessment();
            sb.append("\n=== ЭКОНОМИЧЕСКАЯ ОЦЕНКА ===\n");
            if (ea.getTotalDamageCost() != null) sb.append("Общий ущерб: ").append(ea.getTotalDamageCost()).append("\n");
            if (ea.getInfrastructureDamage() != null) sb.append("Ущерб инфраструктуре: ").append(ea.getInfrastructureDamage()).append("\n");
            if (ea.getCommercialDamage() != null) sb.append("Коммерческий ущерб: ").append(ea.getCommercialDamage()).append("\n");
            if (ea.getTransportDamage() != null) sb.append("Ущерб транспорту: ").append(ea.getTransportDamage()).append("\n");
            if (ea.getRecoveryEstimateDays() != null) sb.append("Дни восстановления: ").append(ea.getRecoveryEstimateDays()).append("\n");
            if (ea.getInsuranceCovered() != null) sb.append("Страховое покрытие: ").append(ea.getInsuranceCovered()).append("\n");
        }


        if (mission.getEnvironmentConditions() != null) {
            EnvironmentConditions env = mission.getEnvironmentConditions();
            sb.append("\n=== УСЛОВИЯ СРЕДЫ ===\n");
            if (env.getWeather() != null && !env.getWeather().isBlank()) {
                sb.append("Погода: ").append(env.getWeather()).append("\n");
            }
            if (env.getTimeOfDay() != null && !env.getTimeOfDay().isBlank()) {
                sb.append("Время суток: ").append(env.getTimeOfDay()).append("\n");
            }
            if (env.getVisibility() != null) sb.append("Видимость: ").append(env.getVisibility()).append("\n");
            if (env.getCursedEnergyDensity() != null) {
                sb.append("Плотность проклятой энергии: ").append(env.getCursedEnergyDensity()).append("\n");
            }
        }

        if (!mission.getOperationTimeline().isEmpty()) {
            sb.append("\n=== ХРОНОЛОГИЯ ===\n");
            for (TimelineEvent ev : mission.getOperationTimeline()) {
                sb.append("  - ").append(ev.getTimestamp())
                        .append(" | ").append(ev.getType())
                        .append(" | ").append(ev.getDescription()).append("\n");
            }
        }


        if (mission.getNotes() != null && !mission.getNotes().isBlank()) {
            sb.append("\n=== ЗАМЕТКИ ===\n");
            sb.append(mission.getNotes()).append("\n");
        }

        return sb.toString();
    }
}