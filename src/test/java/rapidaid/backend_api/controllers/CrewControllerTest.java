package rapidaid.backend_api.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import rapidaid.backend_api.models.Crew;
import rapidaid.backend_api.models.DTOs.CrewDTO;
import rapidaid.backend_api.models.DTOs.mappers.CrewMapper;
import rapidaid.backend_api.models.User;
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

    @Test
    public void shouldReturnAllCrews() throws Exception {
        Crew crew1 = Crew.builder().id("1").crewCount(1).crewType(CrewType.AMBULANCE).crewStatus(CrewStatus.AVAILABLE).longitude(1.0).latitude(1.0).build();
        Crew crew2 = Crew.builder().id("2").crewCount(1).crewType(CrewType.AMBULANCE).crewStatus(CrewStatus.AVAILABLE).longitude(1.0).latitude(1.0).build();
        when(crewService.getAllCrews()).thenReturn(List.of(CrewMapper.mapToCrewDTO(crew1), CrewMapper.mapToCrewDTO(crew2)));

        mockMvc.perform(get("/crews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));

        verify(crewService, times(1)).getAllCrews();
    }
    @Test
    public void shouldCreateCrew() throws Exception {
        String createCrewDTO = "{\"crewCount\":1,\"crewType\":\"POLICE\",\"crewStatus\":\"ON_MISSION\",\"longitude\":0.0,\"latitude\":0.0}";
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
                    .content(createCrewDTO))
                .andExpect(status().isOk())
                .andExpect(content().string(createCrewDTO));

        verify(crewService, times(1)).createCrew(any());
    }

    @Test
    public void shouldReturnCrewById() throws Exception{
        String crewId = "1";
        when(crewService.getCrewById(crewId)).thenReturn(CrewMapper.mapToCrewDTO(new Crew()));

        mockMvc.perform(get("/crews/"+crewId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(5));

        verify(crewService, times(1)).getCrewById(crewId);
    }
    @Test
    public void shouldUpdateCrew() throws Exception {
        mockMvc.perform(put("/crews/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("updateCrew 1"));
    }

    @Test
    public void shouldPatchCrew() throws Exception {
        mockMvc.perform(patch("/crews/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("patchCrew 1"));
    }

    @Test
    public void shouldDeleteCrew() throws Exception {
        String crewId = "1";
        when(crewService.deleteCrew(crewId)).thenReturn(true);

        mockMvc.perform(delete("/crews/"+crewId))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(crewService, times(1)).deleteCrew(crewId);
    }
}
