package rapidaid.backend_api.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import rapidaid.backend_api.services.EmergenceService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmergenceController.class)
public class EmergenceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmergenceService emergenceService;

    @Test
    public void shouldReturnAllEmergencies() throws Exception {
        mockMvc.perform(get("/emergencies"))
                .andExpect(status().isOk())
                .andExpect(content().string("getAllEmergencies"));
    }
    @Test
    public void shouldCreateEmergence() throws Exception {
        String createEmergencyDTO = "{\"location\":\"testLocation\",\"description\":\"testDescription\",\"numberOfPeople\":1}";

        mockMvc.perform(post("/emergencies")
                    .contentType("application/json")
                    .content(createEmergencyDTO))
                .andExpect(status().isOk());
    }
    @Test
    public void shouldReturnEmergenceById() throws Exception {
        mockMvc.perform(get("/emergencies/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("getEmergence 1"));
    }
    @Test
    public void shouldUpdateEmergence() throws Exception {
        mockMvc.perform(put("/emergencies/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("updateEmergence 1"));
    }
    @Test
    public void shouldDeleteEmergence() throws Exception {
        mockMvc.perform(delete("/emergencies/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("deleteEmergence 1"));
    }
}
