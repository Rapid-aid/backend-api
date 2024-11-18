package rapidaid.backend_api.models.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCredentials {
    private String email;
    private String password;
}
