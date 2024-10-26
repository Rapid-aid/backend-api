package rapidaid.backend_api.controllers;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import rapidaid.backend_api.models.DTOs.UserDTO;
import rapidaid.backend_api.models.User;
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
        User user1 = new User("1", "user1", "password1", "", "");
        User user2 = new User("2", "user2", "password2", "", "");
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[1].username").value(user2.getUsername()));
    }

    @Test
    public void shouldCreateUser() throws Exception {
        mockMvc.perform(post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"username\":\"user1\",\"password\":\"password1\",\"email\":\"test@test\"}"))
                .andExpect(status().isOk());

        verify(userService).registerUser(any(UserDTO.class));
    }

    @Test
    public void shouldReturnUserById() throws Exception {
        User user = new User("1", "user1", "password1", "", "");
        when(userService.getUserById(user.getId())).thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.size()", Matchers.is(5)));
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        mockMvc.perform(put("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("updateUser 1"));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        User user = new User("1", "user1", "password1", "", "");
        when(userService.deleteUser(user.getId())).thenReturn(true);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(userService, times(1)).deleteUser(user.getId());
    }
}
