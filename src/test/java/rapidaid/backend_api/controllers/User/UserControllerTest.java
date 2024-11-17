package rapidaid.backend_api.controllers.User;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import rapidaid.backend_api.controllers.AbstractMockMvcTest;
import rapidaid.backend_api.models.DTOs.ChangePasswordDTO;
import rapidaid.backend_api.models.DTOs.UpdateUserDTO;
import rapidaid.backend_api.models.enums.Role;
import rapidaid.backend_api.services.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest extends AbstractMockMvcTest {
    @MockBean
    private UserService userService;

    UpdateUserDTO updateUserDTO = UpdateUserDTO.builder().firstName("testFirstName").lastName("testLastName").email("test@email.com").address("testAddress").phoneNumber("123456789").build();
    ChangePasswordDTO changePasswordDTO = ChangePasswordDTO.builder().email("Test@email.com").newPassword("newPassword").build();
    String userId = "1";

    // getAllUsers
    @Test
    public void getAllUsers_ShouldReturnOkWhenUserIsAdmin() throws Exception {
        setUpMockAuthentication(Role.ADMIN);

        mockMvc.perform(get("/users")
                    .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllUsers_ShouldReturnForbiddenWhenUserIsUser() throws Exception {
        setUpMockAuthentication(Role.USER);

        mockMvc.perform(get("/users")
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAllUsers_ShouldReturnForbiddenWhenUserIsDispatcher() throws Exception {
        setUpMockAuthentication(Role.DISPATCHER);

        mockMvc.perform(get("/users")
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAllUsers_ShouldReturnForbiddenWhenTokenIsInvalid() throws Exception {
        mockMvc.perform(get("/users")
                        .header("Authorization", "Bearer " + "invalidToken"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAllUsers_ShouldReturnForbiddenWhenTokenIsMissing() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isForbidden());
    }
    // changePassword
    @Test
    public void changePassword_ShouldReturnOk() throws Exception {
        setUpMockAuthentication(Role.USER);

        mockMvc.perform(put("/change-password")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(changePasswordDTO))
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());
    }

    @Test
    public void changePassword_ShouldReturnBadRequest() throws Exception {
        setUpMockAuthentication(Role.DISPATCHER);

        mockMvc.perform(put("/change-password")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(ChangePasswordDTO.class))
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void changePassword_ShouldReturnForbiddenWhenTokenIsMissing() throws Exception {
        mockMvc.perform(put("/change-password")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(changePasswordDTO)))
                .andExpect(status().isForbidden());
    }
    // getUser
    @Test
    public void getUser_ShouldReturnOkWhenUserIsAdmin() throws Exception {
        setUpMockAuthentication(Role.ADMIN);

        mockMvc.perform(get("/users/{id}", userId)
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());
    }

    @Test
    public void getUser_ShouldReturnOkWhenUserIsUser() throws Exception {
        setUpMockAuthentication(Role.USER);

        mockMvc.perform(get("/users/{id}", userId)
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());
    }

    @Test
    public void getUser_ShouldReturnForbiddenWhenUserIsDispatcher() throws Exception {
        setUpMockAuthentication(Role.DISPATCHER);

        mockMvc.perform(get("/users/{id}", userId)
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getUser_ShouldReturnForbiddenWhenTokenIsInvalid() throws Exception {
        mockMvc.perform(get("/users/{id}", userId)
                        .header("Authorization", "Bearer " + "invalidToken"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getUser_ShouldReturnForbiddenWhenTokenIsMissing() throws Exception {
        mockMvc.perform(get("/users/{id}", userId))
                .andExpect(status().isForbidden());
    }
    // updateUser
    @Test
    public void updateUser_ShouldReturnOkWhenUserIsAdmin() throws Exception {
        setUpMockAuthentication(Role.ADMIN);

        mockMvc.perform(put("/users/{id}", userId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updateUserDTO))
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());
    }

    @Test
    public void updateUser_ShouldReturnOkWhenUserIsUser() throws Exception {
        setUpMockAuthentication(Role.USER);

        mockMvc.perform(put("/users/{id}", userId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updateUserDTO))
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());
    }

    @Test
    public void updateUser_ShouldReturnForbiddenWhenUserIsDispatcher() throws Exception {
        setUpMockAuthentication(Role.DISPATCHER);

        mockMvc.perform(put("/users/{id}", userId)
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void updateUser_ShouldReturnForbiddenWhenTokenIsInvalid() throws Exception {
        mockMvc.perform(put("/users/{id}", userId)
                        .header("Authorization", "Bearer " + "invalidToken"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void updateUser_ShouldReturnForbiddenWhenTokenIsMissing() throws Exception {
        mockMvc.perform(put("/users/{id}", userId))
                .andExpect(status().isForbidden());
    }
    // deleteUser
    @Test
    public void deleteUser_ShouldReturnOkWhenUserIsAdmin() throws Exception {
        setUpMockAuthentication(Role.ADMIN);

        mockMvc.perform(delete("/users/{id}", userId)
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUser_ShouldReturnOkWhenUserIsUser() throws Exception {
        setUpMockAuthentication(Role.USER);

        mockMvc.perform(delete("/users/{id}", userId)
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUser_ShouldReturnForbiddenWhenUserIsDispatcher() throws Exception {
        setUpMockAuthentication(Role.DISPATCHER);

        mockMvc.perform(delete("/users/{id}", userId)
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteUser_ShouldReturnForbiddenWhenTokenIsInvalid() throws Exception {
        mockMvc.perform(delete("/users/{id}", userId)
                        .header("Authorization", "Bearer " + "invalidToken"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteUser_ShouldReturnForbiddenWhenTokenIsMissing() throws Exception {
        mockMvc.perform(delete("/users/{id}", userId))
                .andExpect(status().isForbidden());
    }
}
