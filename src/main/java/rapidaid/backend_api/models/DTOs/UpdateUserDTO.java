package rapidaid.backend_api.models.DTOs;

import lombok.Data;

@Data
public class UpdateUserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phoneNumber;
}
