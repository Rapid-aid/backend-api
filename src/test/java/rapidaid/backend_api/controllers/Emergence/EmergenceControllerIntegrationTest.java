package rapidaid.backend_api.controllers.Emergence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import rapidaid.backend_api.controllers.AbstractMockMvcTest;
import rapidaid.backend_api.models.DTOs.CreateEmergencyDTO;
import rapidaid.backend_api.models.DTOs.EmergencyDTO;
import rapidaid.backend_api.models.Emergency;
import rapidaid.backend_api.models.enums.Role;
import rapidaid.backend_api.models.enums.Status;
import rapidaid.backend_api.models.enums.Type;
import rapidaid.backend_api.repositories.EmergenceRepository;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EmergenceControllerIntegrationTest extends AbstractMockMvcTest {
    @Autowired
    private EmergenceRepository emergencyRepository;

    @Test
    public void shouldVerifyEmergencyEndpointsIntegration() throws Exception {
        setUpMockAuthentication(Role.DISPATCHER);

        CreateEmergencyDTO createEmergencyDTO1 = CreateEmergencyDTO.builder()
                .numberOfPeople(10)
                .type(Type.FIRE)
                .description("Fire in the building")
                .latitude(10.0)
                .longitude(20.0).build();

        EmergencyDTO emergencyDTO1 = EmergencyDTO.builder()
                .numberOfPeople(10)
                .type(Type.FIRE)
                .status(Status.PENDING)
                .description("Fire in the building")
                .latitude(10.0)
                .longitude(20.0)
                .build();

        mockMvc.perform(post("/emergencies")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createEmergencyDTO1))
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(emergencyDTO1)));

        Emergency savedEmergency1 = emergencyRepository.findAll().getFirst();

        mockMvc.perform(get("/emergencies/{id}", savedEmergency1.getId())
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(emergencyDTO1)));

        CreateEmergencyDTO createEmergencyDTO2 = CreateEmergencyDTO.builder()
                .numberOfPeople(231)
                .type(Type.EARTHQUAKE)
                .description("Earthquake in the city")
                .latitude(15.0)
                .longitude(25.0)
                .build();

        EmergencyDTO emergencyDTO2 = EmergencyDTO.builder()
                .numberOfPeople(231)
                .type(Type.EARTHQUAKE)
                .status(Status.PENDING)
                .description("Earthquake in the city")
                .latitude(15.0)
                .longitude(25.0)
                .build();

        mockMvc.perform(post("/emergencies")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createEmergencyDTO2))
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(emergencyDTO2)));

        mockMvc.perform(get("/emergencies")
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(emergencyDTO1, emergencyDTO2))));

        EmergencyDTO updatedEmergencyDTO = EmergencyDTO.builder()
                .numberOfPeople(52415)
                .type(Type.FIRE)
                .status(Status.PENDING)
                .description("Fire resolved")
                .latitude(10.5)
                .longitude(20.5)
                .build();

        mockMvc.perform(put("/emergencies/{id}", savedEmergency1.getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updatedEmergencyDTO))
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedEmergencyDTO)));

        Emergency savedEmergency2 = emergencyRepository.findAll().get(1);

        mockMvc.perform(get("/emergencies/search")
                        .param("type", Type.EARTHQUAKE.toString())
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(emergencyDTO2))));

        mockMvc.perform(get("/emergencies/search")
                        .param("status", Status.PENDING.toString())
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(updatedEmergencyDTO, emergencyDTO2))));

        mockMvc.perform(delete("/emergencies/{id}", savedEmergency2.getId())
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());

        mockMvc.perform(get("/emergencies")
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(updatedEmergencyDTO))));

        mockMvc.perform(delete("/emergencies/{id}", savedEmergency1.getId())
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());

        mockMvc.perform(get("/emergencies")
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
