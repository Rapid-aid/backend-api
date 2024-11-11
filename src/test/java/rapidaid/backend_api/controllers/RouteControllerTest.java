package rapidaid.backend_api.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(RouteController.class)
public class RouteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnAllRoutes() throws Exception {
        mockMvc.perform(get("/routes"))
                .andExpect(status().isOk())
                .andExpect(content().string("getAllRouters"));
    }

    @Test
    public void shouldReturnRouteById() throws Exception {
        mockMvc.perform(get("/routes/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("getRoute 1"));
    }

    @Test
    public void shouldUpdateRoute() throws Exception {
        mockMvc.perform(patch("/routes/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("updateRoute 1"));
    }
}
