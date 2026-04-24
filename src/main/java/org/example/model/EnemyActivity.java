package org.example.model;
import jakarta.persistence.*;
import org.example.model.enums.*;
import java.util.*;
@Entity public class EnemyActivity { @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
    private String behaviorType;
    @ElementCollection private List<String> targetPriority = new ArrayList<>();
    @ElementCollection private List<String> attackPatterns = new ArrayList<>();
    @Enumerated(EnumType.STRING) private Mobility mobility = Mobility.UNKNOWN;
    @Enumerated(EnumType.STRING) private EscalationRisk escalationRisk = EscalationRisk.UNKNOWN;
    @ElementCollection private List<String> countermeasuresUsed = new ArrayList<>();
    public Long getId(){return id;} public String getBehaviorType(){return behaviorType;}
    public void setBehaviorType(String v){behaviorType=v;}
    public List<String> getTargetPriority(){return targetPriority;}
    public List<String> getAttackPatterns(){return attackPatterns;}
    public Mobility getMobility(){return mobility;}
    public void setMobility(Mobility v){mobility=v;}
    public EscalationRisk getEscalationRisk(){return escalationRisk;}
    public void setEscalationRisk(EscalationRisk v){escalationRisk=v;}
    public List<String> getCountermeasuresUsed(){return countermeasuresUsed;}
}
