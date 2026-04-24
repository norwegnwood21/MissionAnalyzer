package org.example.model;
import jakarta.persistence.*;
import org.example.model.enums.Visibility;
@Entity public class EnvironmentConditions { @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
    private String weather;
    private String timeOfDay;
    @Enumerated(EnumType.STRING) private Visibility visibility = Visibility.UNKNOWN;
    private Integer cursedEnergyDensity;
    public Long getId(){return id;} public String getWeather(){return weather;}
    public void setWeather(String v){weather=v;}
    public String getTimeOfDay(){return timeOfDay;}
    public void setTimeOfDay(String v){timeOfDay=v;}
    public Visibility getVisibility(){return visibility;}
    public void setVisibility(Visibility v){visibility=v;}
    public Integer getCursedEnergyDensity(){return cursedEnergyDensity;}
    public void setCursedEnergyDensity(Integer v){cursedEnergyDensity=v;}
}
