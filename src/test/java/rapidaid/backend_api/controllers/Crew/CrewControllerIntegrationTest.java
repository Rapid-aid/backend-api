package rapidaid.backend_api.controllers.Crew;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import rapidaid.backend_api.controllers.AbstractMockMvcTest;
import rapidaid.backend_api.models.Crew;
import rapidaid.backend_api.models.DTOs.ChangeLocationCrewDTO;
import rapidaid.backend_api.models.DTOs.CrewDTO;
import rapidaid.backend_api.models.DTOs.mappers.CrewMapper;
import rapidaid.backend_api.models.enums.CrewStatus;
import rapidaid.backend_api.models.enums.CrewType;
import rapidaid.backend_api.models.enums.Role;
import rapidaid.backend_api.repositories.CrewRepository;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CrewControllerIntegrationTest  extends AbstractMockMvcTest {
    @Autowired
    private CrewRepository CrewRepository;
    @Autowired
    private CrewRepository crewRepository;

    @Test
    public void shouldVerifyCrewEndpointsIntegration() throws Exception {
        setUpMockAuthentication(Role.DISPATCHER);

        CrewDTO crewDTO1 = CrewDTO.builder().crewCount(1).crewType(CrewType.AMBULANCE).crewStatus(CrewStatus.AVAILABLE).longitude(1.0).latitude(1.0).build();
        mockMvc.perform(post("/crews")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(crewDTO1))
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(crewDTO1)));

        Crew crew1 = CrewRepository.findAll().getFirst();

        mockMvc.perform(get("/crews/{id}", crew1.getId())
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(crewDTO1)));

        CrewDTO crewDTO2 = CrewDTO.builder().crewCount(211).crewType(CrewType.POLICE).crewStatus(CrewStatus.DISPATCHED).longitude(112.0).latitude(2311.0).build();

        mockMvc.perform(post("/crews")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(crewDTO2))
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(crewDTO2)));

        mockMvc.perform(get("/crews")
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(crewDTO1, crewDTO2))));

        CrewDTO updatedCrewDTO = CrewDTO.builder().crewCount(12).crewType(CrewType.POLICE).crewStatus(CrewStatus.ON_MISSION).longitude(1.5).latitude(15.4).build();

        mockMvc.perform(put("/crews/{id}", crew1.getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updatedCrewDTO))
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedCrewDTO)));

        Crew crew2 = CrewRepository.findAll().get(1);
        ChangeLocationCrewDTO changeLocationCrewDTO = ChangeLocationCrewDTO.builder().latitude(50).longitude(42.457).build();

        mockMvc.perform(patch("/crews/{id}", crew2.getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(changeLocationCrewDTO))
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.latitude").value(50))
                .andExpect(jsonPath("$.longitude").value(42.457));

        Crew changedLocationCrewDTO = crewRepository.findById(crew2.getId()).orElse(null);

        assert changedLocationCrewDTO != null;

        mockMvc.perform(get("/crews")
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(updatedCrewDTO, CrewMapper.mapToCrewDTO(changedLocationCrewDTO)))));

        mockMvc.perform(delete("/crews/{id}", crew1.getId())
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());

        mockMvc.perform(get("/crews")
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(CrewMapper.mapToCrewDTO(changedLocationCrewDTO)))));

        mockMvc.perform(delete("/crews/{id}", crew2.getId())
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());

        mockMvc.perform(get("/crews")
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
