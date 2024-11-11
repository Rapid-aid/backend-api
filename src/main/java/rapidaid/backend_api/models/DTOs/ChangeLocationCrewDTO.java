package rapidaid.backend_api.models.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangeLocationCrewDTO {
    private double longitude;
    private double latitude;
}
