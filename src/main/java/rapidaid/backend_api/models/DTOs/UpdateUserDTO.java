package rapidaid.backend_api.models.DTOs;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateUserDTO {
    private String username;
    private String email;
    private String address;
    private String phoneNumber;
}
