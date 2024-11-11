package rapidaid.backend_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import rapidaid.backend_api.models.Crew;
import rapidaid.backend_api.models.DTOs.ChangeLocationCrewDTO;
import rapidaid.backend_api.models.DTOs.CrewDTO;
import rapidaid.backend_api.models.DTOs.mappers.CrewMapper;
import rapidaid.backend_api.models.enums.CrewStatus;
import rapidaid.backend_api.models.enums.CrewType;
import rapidaid.backend_api.services.CrewService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CrewController.class)
public class CrewControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrewService crewService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldReturnAllCrews() throws Exception {
        Crew crew1 = Crew.builder()
                .id("1")
                .crewCount(1)
                .crewType(CrewType.AMBULANCE)
                .crewStatus(CrewStatus.AVAILABLE)
                .longitude(1.0)
                .latitude(1.0).build();
        Crew crew2 = Crew.builder()
                .id("2")
                .crewCount(1)
                .crewType(CrewType.AMBULANCE)
                .crewStatus(CrewStatus.AVAILABLE)
                .longitude(1.0)
                .latitude(1.0).build();

        when(crewService.getAllCrews()).thenReturn(List.of(CrewMapper.mapToCrewDTO(crew1), CrewMapper.mapToCrewDTO(crew2)));

        mockMvc.perform(get("/crews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));

        verify(crewService, times(1)).getAllCrews();
    }
    @Test
    public void shouldCreateCrew() throws Exception {
        CrewDTO crewDTO = CrewDTO.builder()
                .crewCount(1)
                .crewType(CrewType.POLICE)
                .crewStatus(CrewStatus.ON_MISSION)
                .longitude(0.0)
                .latitude(0.0)
                .build();
        when(crewService.createCrew(any(CrewDTO.class))).thenReturn(crewDTO);

        mockMvc.perform(post("/crews")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(crewDTO)))
                .andExpect(status().isOk());

        verify(crewService, times(1)).createCrew(any());
    }

    @Test
    public void shouldReturnCrewById() throws Exception{
        String crewId = "1";
        CrewDTO crewDTO = CrewDTO.builder()
                .crewCount(1)
                .crewType(CrewType.AMBULANCE)
                .crewStatus(CrewStatus.AVAILABLE)
                .longitude(1.0)
                .latitude(1.0)
                .build();

        when(crewService.getCrewById(crewId)).thenReturn(crewDTO);

        mockMvc.perform(get("/crews/{id}", crewId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(crewDTO)));

        verify(crewService, times(1)).getCrewById(crewId);
    }
    @Test
    public void shouldUpdateCrew() throws Exception {
        String crewId = "1";
        CrewDTO crewDTO = CrewDTO.builder()
                .crewCount(1)
                .crewType(CrewType.POLICE)
                .crewStatus(CrewStatus.ON_MISSION)
                .longitude(0.0)
                .latitude(0.0)
                .build();

        when(crewService.updateCrew(crewDTO, crewId)).thenReturn(crewDTO);

        mockMvc.perform(put("/crews/{id}", crewId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(crewDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(crewDTO)));

        verify(crewService, times(1)).updateCrew(crewDTO, crewId);
    }

    @Test
    public void shouldChangeLocationCrew() throws Exception {
        String crewId = "1";
        ChangeLocationCrewDTO changeLocationCrewDTO = ChangeLocationCrewDTO.builder()
                .longitude(1.0)
                .latitude(1.0)
                .build();

        CrewDTO crewDTO = CrewDTO.builder()
                .crewCount(1)
                .crewType(CrewType.AMBULANCE)
                .crewStatus(CrewStatus.AVAILABLE)
                .longitude(1.0)
                .latitude(1.0)
                .build();

        when(crewService.changeLocationCrew(changeLocationCrewDTO, crewId)).thenReturn(crewDTO);

        mockMvc.perform(patch("/crews/{id}", crewId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(changeLocationCrewDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(crewDTO)));

        verify(crewService, times(1)).changeLocationCrew(changeLocationCrewDTO, crewId);
    }

    @Test
    public void shouldDeleteCrew() throws Exception {
        String crewId = "1";
        when(crewService.deleteCrew(crewId)).thenReturn(true);

        mockMvc.perform(delete("/crews/{id}", crewId))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(crewService, times(1)).deleteCrew(crewId);
    }
}
