package rapidaid.backend_api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import rapidaid.backend_api.models.DTOs.AuthenticationResponse;
import rapidaid.backend_api.models.DTOs.CreateUserDTO;
import rapidaid.backend_api.models.DTOs.UserCredentials;
import rapidaid.backend_api.models.User;
import rapidaid.backend_api.models.enums.Role;
import rapidaid.backend_api.repositories.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister_Success() {
        CreateUserDTO createUserDTO = CreateUserDTO.builder().email("test@example.com").password("password123").build();
        User user = new User();
        user.setEmail(createUserDTO.getEmail());
        user.setPassword("encodedPassword");
        user.setRole(Role.USER);

        when(passwordEncoder.encode(createUserDTO.getPassword())).thenReturn("encodedPassword");
        when(userRepository.findByEmail(createUserDTO.getEmail())).thenReturn(Optional.empty());
        when(jwtService.generateToken(any(User.class))).thenReturn("mockedToken");

        AuthenticationResponse response = authService.register(createUserDTO);

        assertNotNull(response);
        assertEquals("mockedToken", response.getToken());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegister_UserAlreadyExists() {
        CreateUserDTO createUserDTO = CreateUserDTO.builder().email("test@example.com").password("password123").build();
        when(userRepository.findByEmail(createUserDTO.getEmail())).thenReturn(Optional.of(new User()));

        assertThrows(IllegalArgumentException.class, () -> authService.register(createUserDTO));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testAuthenticate_Success() {
        UserCredentials credentials = UserCredentials.builder().email("test@example.com").password("password123").build();
        User user = new User();
        user.setEmail(credentials.getEmail());
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail(credentials.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("mockedToken");

        AuthenticationResponse response = authService.authenticate(credentials);

        assertNotNull(response);
        assertEquals("mockedToken", response.getToken());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void testAuthenticate_InvalidCredentials() {
        UserCredentials credentials = UserCredentials.builder().email("test@example.com").password("wrongPassword").build();
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(BadCredentialsException.class);

        assertThrows(BadCredentialsException.class, () -> authService.authenticate(credentials));
    }
}
