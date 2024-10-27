package rapidaid.backend_api.controllers;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import rapidaid.backend_api.models.DTOs.UpdateUserDTO;
import rapidaid.backend_api.models.DTOs.UserDTO;
import rapidaid.backend_api.models.User;
import rapidaid.backend_api.models.enums.Role;
import rapidaid.backend_api.services.UserService;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void shouldReturnAllUsers() throws Exception {
        User user1 = User.builder().id("1").username("testUser1").password("password").email("test1@exapmle.com").role(Role.RESPONDER).build();
        User user2 = User.builder().id("2").username("testUser2").password("password").email("test2@exapmle.com").role(Role.RESPONDER).build();
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[1].username").value(user2.getUsername()));
    }

    @Test
    public void shouldCreateUser() throws Exception {
        String createUserDTO = "{\"username\":\"testUser\",\"password\":\"password\",\"email\":\"test@example.com\",\"address\":\"test address\",\"phoneNumber\":\"123456789\"}";
        UserDTO userDTO = UserDTO.builder().username("testUser").email("test@example.com").build();
        when(userService.registerUser(any(User.class))).thenReturn(userDTO);

        mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(createUserDTO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(userDTO.getUsername()))
                .andExpect(jsonPath("$.email").value(userDTO.getEmail()))
                .andExpect(jsonPath("$.role").value(userDTO.getRole()));


        verify(userService).registerUser(any(User.class));
    }

    @Test
    public void shouldReturnUserById() throws Exception {
        User user = User.builder().id("1").username("testUser1").password("password").email("test1@exapmle.com").role(Role.RESPONDER).build();
        when(userService.getUserById(user.getId())).thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.size()", Matchers.is(7)));
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        String updateUserDTO = "{\"username1\":\"user1\",\"email\":\"test1@test\"}";
        User user = User.builder().id("1").username("testUser1").password("password").email("test1@example.com").build();
        UserDTO userDTO = UserDTO.builder().username("username1").email("test1@test.com").build();

        when(userService.updateUser(eq(user.getId()), any(UpdateUserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(put("/users/{id}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateUserDTO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(userDTO.getUsername()))
                .andExpect(jsonPath("$.email").value(userDTO.getEmail()));

    }

    @Test
    public void shouldDeleteUser() throws Exception {
        User user = User.builder().id("1").username("testUser1").password("password").email("test1@exapmle.com").role(Role.RESPONDER).build();
        when(userService.deleteUser(user.getId())).thenReturn(true);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(userService, times(1)).deleteUser(user.getId());
    }
}
