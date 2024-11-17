package rapidaid.backend_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import rapidaid.backend_api.models.enums.Role;
import rapidaid.backend_api.services.JwtService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public abstract class AbstractMockMvcTest {
    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected JwtService jwtService;

    @MockBean
    protected UserDetailsService userDetailsService;

    protected ObjectMapper objectMapper = new ObjectMapper();

    protected String mockToken = "mockedToken";

    protected void setUpMockAuthentication(Role role) {
        UserDetails userDetails = User.builder()
                .username("email")
                .password("password")
                .roles(role.name())
                .build();

        when(userDetailsService.loadUserByUsername(userDetails.getUsername())).thenReturn(userDetails);

        when(jwtService.generateToken(any(UserDetails.class))).thenReturn(mockToken);
        when(jwtService.extractEmail(mockToken)).thenReturn(userDetails.getUsername());
        when(jwtService.isTokenValid(eq(mockToken), eq(userDetails))).thenReturn(true);
    }
}
