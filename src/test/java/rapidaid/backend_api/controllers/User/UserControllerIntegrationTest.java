package rapidaid.backend_api.controllers.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import rapidaid.backend_api.controllers.AbstractMockMvcTest;
import rapidaid.backend_api.models.DTOs.ChangePasswordDTO;
import rapidaid.backend_api.models.DTOs.CreateUserDTO;
import rapidaid.backend_api.models.DTOs.UpdateUserDTO;
import rapidaid.backend_api.models.DTOs.UserDTO;
import rapidaid.backend_api.models.User;
import rapidaid.backend_api.models.enums.Role;
import rapidaid.backend_api.repositories.UserRepository;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest extends AbstractMockMvcTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldVerifyUserEndpointsIntegration() throws Exception {
        setUpMockAuthentication(Role.ADMIN);

        CreateUserDTO createUserDTO = CreateUserDTO.builder()
                .firstName("Test")
                .lastName("User")
                .password("password123")
                .email("testuser@example.com")
                .address("address")
                .phoneNumber("123456789")
                .build();

        UserDTO createdUser = UserDTO.builder()
                .firstName("Test")
                .lastName("User")
                .role(Role.USER)
                .email("testuser@example.com")
                .build();

        mockMvc.perform(post("/auth/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createUserDTO)))
                .andExpect(status().isOk())
                .andDo(print());

        User savedUser = userRepository.findAll().getLast();

        mockMvc.perform(get("/users/{id}", savedUser.getId())
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(createdUser)));

        UpdateUserDTO updateUserDTO = UpdateUserDTO.builder()
                .firstName("Updated")
                .lastName("User")
                .email("newemail@example.com")
                .address("new address")
                .phoneNumber("123456789")
                .build();

        UserDTO updatedUserDTO = UserDTO.builder()
                .firstName("Updated")
                .lastName("User")
                .email("newemail@example.com")
                .role(Role.USER)
                .build();

        mockMvc.perform(put("/users/{id}", savedUser.getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updateUserDTO))
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedUserDTO)));

        ChangePasswordDTO changePasswordDTO = ChangePasswordDTO.builder()
                .email("newemail@example.com")
                .newPassword("newPassword123")
                .build();

        mockMvc.perform(put("/change-password")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(changePasswordDTO))
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());

        List<User> users = userRepository.findAll();

        mockMvc.perform(get("/users")
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(users)))
                .andExpect(jsonPath("$.size()").value(users.size()));

        mockMvc.perform(delete("/users/{id}", savedUser.getId())
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());
    }
}
