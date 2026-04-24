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
            sb.append("\nПроклятие:\n");
            sb.append("  Название: ").append(mission.getCurse().getName()).append("\n");
            sb.append("  Уровень угрозы: ").append(mission.getCurse().getThreatLevel()).append("\n");
        }

        if (!mission.getSorcerers().isEmpty()) {
            sb.append("\nУчастники:\n");
            for (Sorcerer s : mission.getSorcerers()) {
                sb.append("  - ").append(s.getName());
                if (s.getRank() != null) {
                    sb.append(" (").append(s.getRank()).append(")");
                }
                sb.append("\n");
            }
        }

        if (!mission.getTechniques().isEmpty()) {
            sb.append("\nТехники:\n");
            for (Technique t : mission.getTechniques()) {
                sb.append("  - ").append(t.getName());

                if (t.getType() != null) {
                    sb.append(" [").append(t.getType()).append("]");
                }
                if (t.getOwner() != null && !t.getOwner().isBlank()) {
                    sb.append(", владелец: ").append(t.getOwner());
                }
                if (t.getDamage() != null) {
                    sb.append(", урон: ").append(t.getDamage());
                }
                sb.append("\n");
            }
        }

        if (mission.getEconomicAssessment() != null) {
            EconomicAssessment e = mission.getEconomicAssessment();
            sb.append("\nЭкономическая оценка:\n");
            if (e.getTotalDamageCost() != null) {
                sb.append("  Общий ущерб: ").append(e.getTotalDamageCost()).append("\n");
            }
            if (e.getInfrastructureDamage() != null) {
                sb.append("  Ущерб инфраструктуре: ").append(e.getInfrastructureDamage()).append("\n");
            }
            if (e.getCommercialDamage() != null) {
                sb.append("  Коммерческий ущерб: ").append(e.getCommercialDamage()).append("\n");
            }
            if (e.getTransportDamage() != null) {
                sb.append("  Ущерб транспорту: ").append(e.getTransportDamage()).append("\n");
            }
            if (e.getRecoveryEstimateDays() != null) {
                sb.append("  Восстановление (дней): ").append(e.getRecoveryEstimateDays()).append("\n");
            }
            if (e.getInsuranceCovered() != null) {
                sb.append("  Страховое покрытие: ").append(e.getInsuranceCovered()).append("\n");
            }
        }

        if (mission.getCivilianImpact() != null) {
            CivilianImpact c = mission.getCivilianImpact();
            sb.append("\nВлияние на гражданских:\n");
            if (c.getEvacuated() != null) {
                sb.append("  Эвакуировано: ").append(c.getEvacuated()).append("\n");
            }
            if (c.getInjured() != null) {
                sb.append("  Ранено: ").append(c.getInjured()).append("\n");
            }
            if (c.getMissing() != null) {
                sb.append("  Пропавшие: ").append(c.getMissing()).append("\n");
            }
            if (c.getPublicExposureRisk() != null) {
                sb.append("  Риск огласки: ").append(c.getPublicExposureRisk()).append("\n");
            }
        }if (mission.getEnemyActivity() != null) {
            EnemyActivity e = mission.getEnemyActivity();
            sb.append("\nАктивность противника:\n");
            if (e.getBehaviorType() != null) {
                sb.append("  Тип поведения: ").append(e.getBehaviorType()).append("\n");
            }
            if (e.getMobility() != null) {
                sb.append("  Мобильность: ").append(e.getMobility()).append("\n");
            }
            if (e.getEscalationRisk() != null) {
                sb.append("  Риск эскалации: ").append(e.getEscalationRisk()).append("\n");
            }
            if (!e.getTargetPriority().isEmpty()) {
                sb.append("  Приоритет целей: ").append(String.join(", ", e.getTargetPriority())).append("\n");
            }
            if (!e.getAttackPatterns().isEmpty()) {
                sb.append("  Паттерны атак: ").append(String.join(", ", e.getAttackPatterns())).append("\n");
            }
            if (!e.getCountermeasuresUsed().isEmpty()) {
                sb.append("  Контрмеры: ").append(String.join(", ", e.getCountermeasuresUsed())).append("\n");
            }
        }

        if (mission.getEnvironmentConditions() != null) {
            EnvironmentConditions env = mission.getEnvironmentConditions();
            sb.append("\nУсловия среды:\n");
            if (env.getWeather() != null) {
                sb.append("  Погода: ").append(env.getWeather()).append("\n");
            }
            if (env.getTimeOfDay() != null) {
                sb.append("  Время суток: ").append(env.getTimeOfDay()).append("\n");
            }
            if (env.getVisibility() != null) {
                sb.append("  Видимость: ").append(env.getVisibility()).append("\n");
            }
            if (env.getCursedEnergyDensity() != null) {
                sb.append("  Плотность проклятой энергии: ").append(env.getCursedEnergyDensity()).append("\n");
            }
        }

        if (!mission.getOperationTimeline().isEmpty()) {
            sb.append("\nХронология:\n");
            for (TimelineEvent ev : mission.getOperationTimeline()) {
                sb.append("  - ");
                if (ev.getTimestamp() != null) {
                    sb.append(ev.getTimestamp()).append(" | ");
                }
                sb.append(ev.getType()).append(" | ").append(ev.getDescription()).append("\n");
            }
        }

        if (!mission.getOperationTags().isEmpty()) {
            sb.append("\nТеги операции: ").append(String.join(", ", mission.getOperationTags())).append("\n");
        }

        if (!mission.getSupportUnits().isEmpty()) {
            sb.append("Подразделения поддержки: ").append(String.join(", ", mission.getSupportUnits())).append("\n");
        }

        if (!mission.getRecommendations().isEmpty()) {
            sb.append("Рекомендации: ").append(String.join(", ", mission.getRecommendations())).append("\n");
        }

        if (!mission.getArtifactsRecovered().isEmpty()) {
            sb.append("Изъятые артефакты: ").append(String.join(", ", mission.getArtifactsRecovered())).append("\n");
        }

        if (!mission.getEvacuationZones().isEmpty()) {
            sb.append("Зоны эвакуации: ").append(String.join(", ", mission.getEvacuationZones())).append("\n");
        }

        if (!mission.getStatusEffects().isEmpty()) {
            sb.append("Статусные эффекты: ").append(String.join(", ", mission.getStatusEffects())).append("\n");
        }

        if (mission.getNotes() != null && !mission.getNotes().isBlank()) {
            sb.append("\nПримечания:\n");
            sb.append(mission.getNotes()).append("\n");
        }

        return sb.toString();
    }
}