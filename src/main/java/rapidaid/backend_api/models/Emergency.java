package rapidaid.backend_api.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rapidaid.backend_api.models.enums.PriorityLevel;
import rapidaid.backend_api.models.enums.Status;
import rapidaid.backend_api.models.enums.Type;

@Entity
@Table(name = "EMERGENCIES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Emergency {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Integer numberOfPeople;
    private Type type;
    private Status status;
    private String description;
    private PriorityLevel priorityLevel;
    private Double latitude;
    private Double longitude;
}
