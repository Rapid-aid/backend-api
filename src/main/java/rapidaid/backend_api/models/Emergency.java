package rapidaid.backend_api.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rapidaid.backend_api.models.enums.PriorityLevel;
import rapidaid.backend_api.models.enums.Status;

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
    private Status status;
    private PriorityLevel priorityLevel;
    private String location;
    private String description;
    private Integer numberOfPeople;
}
