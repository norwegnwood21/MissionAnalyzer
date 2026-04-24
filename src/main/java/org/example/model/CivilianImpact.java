package org.example.model;
import jakarta.persistence.*;
import org.example.model.enums.PublicExposureRisk;
@Entity public class CivilianImpact { @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
    private Integer evacuated;
    private Integer injured;
    private Integer missing;
    @Enumerated(EnumType.STRING)
    private PublicExposureRisk publicExposureRisk = PublicExposureRisk.UNKNOWN;
    public Long getId(){return id;}
    public Integer getEvacuated(){return evacuated;}
    public void setEvacuated(Integer v){evacuated=v;}
    public Integer getInjured(){return injured;}
    public void setInjured(Integer v){injured=v;}
    public Integer getMissing(){return missing;}
    public void setMissing(Integer v){missing=v;}
    public PublicExposureRisk getPublicExposureRisk(){return publicExposureRisk;}
    public void setPublicExposureRisk(PublicExposureRisk v){publicExposureRisk=v;}
}
