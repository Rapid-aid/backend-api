package rapidaid.backend_api.models.DTOs;

import lombok.Builder;
import lombok.Data;
import rapidaid.backend_api.models.enums.Type;

@Data
@Builder
public class CreateEmergencyDTO {
    private Integer numberOfPeople;
    private Type type;
    private String description;
    private Double latitude;
    private Double longitude;
}
