package rapidaid.backend_api.models.DTOs;

import lombok.Builder;
import lombok.Data;
import rapidaid.backend_api.models.enums.CrewStatus;
import rapidaid.backend_api.models.enums.CrewType;

@Data
@Builder
public class CrewDTO {
    private Integer crewCount;
    private CrewType crewType;
    private CrewStatus crewStatus;
    private double longitude;
    private double latitude;
}
