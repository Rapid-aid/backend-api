package rapidaid.backend_api.models.DTOs;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateUserDTO {
    private String username;
    private String password;
    private String email;
    private String address;
    private String phoneNumber;
}
