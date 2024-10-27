package rapidaid.backend_api.models.DTOs;

import lombok.*;
import rapidaid.backend_api.models.enums.Role;

@Data
@Builder
public class UserDTO {
    private String username;
    private String email;
    private Role role;
}
