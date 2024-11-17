package rapidaid.backend_api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rapidaid.backend_api.models.DTOs.AuthenticationResponse;
import rapidaid.backend_api.models.DTOs.CreateUserDTO;
import rapidaid.backend_api.models.DTOs.UserCredentials;
import rapidaid.backend_api.models.DTOs.mappers.UserMapper;
import rapidaid.backend_api.models.User;
import rapidaid.backend_api.models.enums.Role;
import rapidaid.backend_api.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthenticationResponse register(CreateUserDTO createUserDTO) {
        User user = UserMapper.mapToUser(createUserDTO);
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        user.setRole(Role.USER);
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        userRepository.save(user);

        return AuthenticationResponse.builder().token(jwtService.generateToken(user)).build();
    }

    public AuthenticationResponse authenticate(UserCredentials userCredentials) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCredentials.getEmail(), userCredentials.getPassword()));
        User user = userRepository.findByEmail(userCredentials.getEmail()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return AuthenticationResponse.builder().token(jwtService.generateToken(user)).build();
    }
}
