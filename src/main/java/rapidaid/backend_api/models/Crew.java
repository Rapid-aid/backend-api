package rapidaid.backend_api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rapidaid.backend_api.models.enums.CrewStatus;
import rapidaid.backend_api.models.enums.CrewType;

@Entity
@Table(name = "CREWS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Crew {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Integer crewCount;
    private CrewType crewType;
    private CrewStatus crewStatus;
    private double longitude;
    private double latitude;
}
