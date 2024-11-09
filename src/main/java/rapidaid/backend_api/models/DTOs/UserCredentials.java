package rapidaid.backend_api.models.DTOs;

import lombok.Data;

@Data
public class UserCredentials {
    private String email;
    private String password;
}
