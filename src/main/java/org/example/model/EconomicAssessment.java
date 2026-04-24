package org.example.model;
import jakarta.persistence.*;
@Entity public class EconomicAssessment { @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
    private Long totalDamageCost;
    private Long infrastructureDamage;
    private Long commercialDamage;
    private Long transportDamage;
    private Integer recoveryEstimateDays;
    private Boolean insuranceCovered;
    public Long getId(){return id;}
    public Long getTotalDamageCost(){return totalDamageCost;}
    public void setTotalDamageCost(Long v){totalDamageCost=v;}
    public Long getInfrastructureDamage(){return infrastructureDamage;}
    public void setInfrastructureDamage(Long v){infrastructureDamage=v;}
    public Long getCommercialDamage(){return commercialDamage;}
    public void setCommercialDamage(Long v){commercialDamage=v;}
    public Long getTransportDamage(){return transportDamage;}
    public void setTransportDamage(Long v){transportDamage=v;}
    public Integer getRecoveryEstimateDays(){return recoveryEstimateDays;}
    public void setRecoveryEstimateDays(Integer v){recoveryEstimateDays=v;}
    public Boolean getInsuranceCovered(){return insuranceCovered;}
    public void setInsuranceCovered(Boolean v){insuranceCovered=v;}
}
