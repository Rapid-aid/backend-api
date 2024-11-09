package rapidaid.backend_api.models.DTOs;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    String token;
}
