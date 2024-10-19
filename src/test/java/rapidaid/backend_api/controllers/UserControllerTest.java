package rapidaid.backend_api.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import rapidaid.backend_api.services.UserService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void shouldReturnAllUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().string("getAllUsers"));
    }

    @Test
    public void shouldCreateUser() throws Exception {
        mockMvc.perform(post("/users"))
                .andExpect(status().isOk())
                .andExpect(content().string("createUser"));
    }

    @Test
    public void shouldReturnUserById() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("getUser 1"));
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        mockMvc.perform(put("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("updateUser 1"));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("deleteUser 1"));
    }
}
