package rapidaid.backend_api.models.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangePasswordDTO{
    private String email;
    private String newPassword;
}
