package rapidaid.backend_api.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rapidaid.backend_api.models.DTOs.AuthenticationResponse;
import rapidaid.backend_api.models.DTOs.CreateUserDTO;
import rapidaid.backend_api.models.DTOs.UserCredentials;
import rapidaid.backend_api.services.AuthService;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody CreateUserDTO createUserDTO) {
        return authService.register(createUserDTO);
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(@RequestBody UserCredentials userCredentials) {
        return authService.authenticate(userCredentials);
    }
}
