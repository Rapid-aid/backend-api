package rapidaid.backend_api.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePasswordDTO{
    private String email;
    private String newPassword;
}
