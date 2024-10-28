package rapidaid.backend_api.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePassword {
    private String email;
    private String newPassword;
}
