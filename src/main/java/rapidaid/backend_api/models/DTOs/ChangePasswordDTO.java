package rapidaid.backend_api.models.DTOs;

import lombok.Data;

@Data
public class ChangePasswordDTO{
    private String email;
    private String newPassword;
}
