package rapidaid.backend_api.models.DTOs;

import lombok.Builder;
import lombok.Data;
import rapidaid.backend_api.models.enums.PriorityLevel;
import rapidaid.backend_api.models.enums.Status;

@Data
@Builder
public class EmergencyDTO {
    private Status status;
    private PriorityLevel priorityLevel;
    private String location;
    private String description;
    private Integer numberOfPeople;
}
