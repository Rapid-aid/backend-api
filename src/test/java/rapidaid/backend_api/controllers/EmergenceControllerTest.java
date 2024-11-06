package rapidaid.backend_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import rapidaid.backend_api.models.DTOs.CreateEmergencyDTO;
import rapidaid.backend_api.models.DTOs.CreateUserDTO;
import rapidaid.backend_api.models.DTOs.EmergencyDTO;
import rapidaid.backend_api.services.EmergenceService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmergenceController.class)
public class EmergenceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmergenceService emergenceService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldReturnAllEmergencies() throws Exception {
        EmergencyDTO emergencyDTO1 = EmergencyDTO.builder()
                .location("testLocation")
                .description("testDescription")
                .numberOfPeople(1).build();
        EmergencyDTO emergencyDTO2 = EmergencyDTO.builder()
                .location("testLocation")
                .description("testDescription")
                .numberOfPeople(1).build();

        when(emergenceService.getAllEmergencies()).thenReturn(List.of(emergencyDTO1, emergencyDTO2));

        mockMvc.perform(get("/emergencies"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(List.of(emergencyDTO1, emergencyDTO2))));

        verify(emergenceService, times(1)).getAllEmergencies();
    }
    @Test
    public void shouldCreateEmergence() throws Exception {
        CreateEmergencyDTO createEmergencyDTO1 = CreateEmergencyDTO.builder()
                .location("testLocation")
                .description("testDescription")
                .numberOfPeople(1).build();

        mockMvc.perform(post("/emergencies")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(createEmergencyDTO1)))
                .andExpect(status().isOk());
    }
    @Test
    public void shouldReturnEmergenceById() throws Exception {
        String emergencyId = "1";
        EmergencyDTO emergencyDTO = EmergencyDTO.builder()
                .location("testLocation")
                .description("testDescription")
                .numberOfPeople(1).build();

        when(emergenceService.getEmergence(emergencyId)).thenReturn(emergencyDTO);

        mockMvc.perform(get("/emergencies/{id}", emergencyId))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(emergencyDTO)));

        verify(emergenceService, times(1)).getEmergence(emergencyId);
    }
    @Test
    public void shouldUpdateEmergence() throws Exception {
        String emergencyId = "1";
        EmergencyDTO emergencyDTO = EmergencyDTO.builder()
                .location("testLocation")
                .description("testDescription")
                .numberOfPeople(1).build();

        when(emergenceService.updateEmergence(emergencyId, emergencyDTO)).thenReturn(emergencyDTO);

        mockMvc.perform(put("/emergencies/{id}", emergencyId)
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(emergencyDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(emergencyDTO)));

        verify(emergenceService, times(1)).updateEmergence(emergencyId, emergencyDTO);
    }
    @Test
    public void shouldDeleteEmergence() throws Exception {
        String emergencyId = "1";

        when(emergenceService.deleteEmergence(emergencyId)).thenReturn(true);

        mockMvc.perform(delete("/emergencies/{id}", emergencyId))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(emergenceService, times(1)).deleteEmergence(emergencyId);
    }
}
