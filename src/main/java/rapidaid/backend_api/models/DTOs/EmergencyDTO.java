package rapidaid.backend_api.models.DTOs;

import lombok.Builder;
import lombok.Data;
import rapidaid.backend_api.models.enums.PriorityLevel;
import rapidaid.backend_api.models.enums.Status;
import rapidaid.backend_api.models.enums.Type;

@Data
@Builder
public class EmergencyDTO {
    private Integer numberOfPeople;
    private Type type;
    private Status status;
    private String description;
    private PriorityLevel priorityLevel;
    private Double latitude;
    private Double longitude;
}
