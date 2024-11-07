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
import rapidaid.backend_api.models.enums.PriorityLevel;
import rapidaid.backend_api.models.enums.Status;
import rapidaid.backend_api.models.enums.Type;
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
                .numberOfPeople(1)
                .type(Type.ACCIDENT)
                .status(Status.PENDING)
                .description("testDescription")
                .latitude(1.0)
                .longitude(1.0).build();
        EmergencyDTO emergencyDTO2 = EmergencyDTO.builder()
                .numberOfPeople(2)
                .type(Type.ACCIDENT)
                .status(Status.PENDING)
                .description("testDescription")
                .latitude(2.0)
                .longitude(2.0).build();

        when(emergenceService.getAllEmergencies()).thenReturn(List.of(emergencyDTO1, emergencyDTO2));

        mockMvc.perform(get("/emergencies"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(emergencyDTO1, emergencyDTO2))));

        verify(emergenceService, times(1)).getAllEmergencies();
    }
    @Test
    public void shouldCreateEmergence() throws Exception {
        CreateEmergencyDTO createEmergencyDTO1 = CreateEmergencyDTO.builder()
                .numberOfPeople(1)
                .type(Type.ACCIDENT)
                .description("testDescription")
                .latitude(1.0)
                .longitude(1.0).build();

        mockMvc.perform(post("/emergencies")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(createEmergencyDTO1)))
                .andExpect(status().isOk());
    }
    @Test
    public void shouldReturnEmergenceById() throws Exception {
        String emergencyId = "1";
        EmergencyDTO emergencyDTO = EmergencyDTO.builder()
                .numberOfPeople(1)
                .type(Type.ACCIDENT)
                .status(Status.PENDING)
                .description("testDescription")
                .priorityLevel(PriorityLevel.MEDIUM)
                .latitude(1.0)
                .longitude(1.0).build();

        when(emergenceService.getEmergence(emergencyId)).thenReturn(emergencyDTO);

        mockMvc.perform(get("/emergencies/{id}", emergencyId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(emergencyDTO)));

        verify(emergenceService, times(1)).getEmergence(emergencyId);
    }

    @Test
    public void shouldReturnSearchEmergence() throws Exception {
        String status = Status.CANCELLED.getStatus();
        String type = Type.ACCIDENT.getType();
        String keyword = "testDescription";

        EmergencyDTO emergencyDTO = EmergencyDTO.builder()
                .numberOfPeople(1)
                .type(Type.ACCIDENT)
                .status(Status.CANCELLED)
                .description("testDescription")
                .priorityLevel(PriorityLevel.MEDIUM)
                .latitude(1.0)
                .longitude(1.0).build();

        when(emergenceService.searchEmergencies(type, status, keyword)).thenReturn(List.of(emergencyDTO));

        mockMvc.perform(get("/emergencies/search")
                    .param("type", type)
                    .param("status", status)
                    .param("keyword", keyword))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(emergencyDTO))));

        verify(emergenceService, times(1)).searchEmergencies(type, status, keyword);
    }

    @Test
    public void shouldUpdateEmergence() throws Exception {
        String emergencyId = "1";
        EmergencyDTO emergencyDTO = EmergencyDTO.builder()
                .numberOfPeople(1)
                .type(Type.ACCIDENT)
                .status(Status.PENDING)
                .description("testDescription")
                .priorityLevel(PriorityLevel.MEDIUM)
                .latitude(1.0)
                .longitude(1.0).build();

        when(emergenceService.updateEmergence(emergencyId, emergencyDTO)).thenReturn(emergencyDTO);

        mockMvc.perform(put("/emergencies/{id}", emergencyId)
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(emergencyDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(emergencyDTO)));

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
