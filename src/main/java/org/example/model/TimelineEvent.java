package org.example.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity public class TimelineEvent { @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
    private LocalDateTime timestamp;
    private String type;
    private String description;
    public Long getId(){return id;}
    public LocalDateTime getTimestamp(){return timestamp;}
    public void setTimestamp(LocalDateTime v){timestamp=v;}
    public String getType(){return type;}
    public void setType(String v){type=v;}
    public String getDescription(){return description;}
    public void setDescription(String v){description=v;}
}
