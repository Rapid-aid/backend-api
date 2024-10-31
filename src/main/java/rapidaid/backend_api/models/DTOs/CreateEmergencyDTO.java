package rapidaid.backend_api.models.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateEmergencyDTO {
    private String location;
    private String description;
    private Integer numberOfPeople;
}
