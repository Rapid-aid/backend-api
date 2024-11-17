package rapidaid.backend_api.controllers.Crew;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import rapidaid.backend_api.controllers.AbstractMockMvcTest;
import rapidaid.backend_api.models.DTOs.ChangeLocationCrewDTO;
import rapidaid.backend_api.models.DTOs.CrewDTO;
import rapidaid.backend_api.models.enums.CrewStatus;
import rapidaid.backend_api.models.enums.CrewType;
import rapidaid.backend_api.models.enums.Role;
import rapidaid.backend_api.services.CrewService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CrewControllerTest extends AbstractMockMvcTest {
    @MockBean
    private CrewService crewService;

    CrewDTO crewDTO = CrewDTO.builder().crewCount(1).crewType(CrewType.POLICE).crewStatus(CrewStatus.ON_MISSION).longitude(0.0).latitude(0.0).build();
    ChangeLocationCrewDTO changeLocationCrewDTO = ChangeLocationCrewDTO.builder().longitude(1.0).latitude(1.0).build();
    String crewId = "1";

    // getAllCrews
    @Test
    public void getAllCrews_ShouldReturnOkWhenUserIsDispatcher() throws Exception {
        setUpMockAuthentication(Role.DISPATCHER);

        mockMvc.perform(get("/crews")
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllCrews_ShouldReturnForbiddenWhenUserIsAdmin() throws Exception {
        setUpMockAuthentication(Role.ADMIN);

        mockMvc.perform(get("/crews")
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAllCrews_ShouldReturnForbiddenWhenUserIsUser() throws Exception {
        setUpMockAuthentication(Role.USER);

        mockMvc.perform(get("/crews")
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAllCrews_ShouldReturnForbiddenWhenTokenIsInvalid() throws Exception {
        mockMvc.perform(get("/crews")
                        .header("Authorization", "Bearer " + "invalidToken"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAllCrews_ShouldReturnForbiddenWhenTokenIsMissing() throws Exception {
        mockMvc.perform(get("/crews"))
                .andExpect(status().isForbidden());
    }
    // createCrew
    @Test
    public void createCrew_ShouldReturnOkWhenUserIsDispatcher() throws Exception {
        setUpMockAuthentication(Role.DISPATCHER);

        mockMvc.perform(post("/crews")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(crewDTO))
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());
    }

    @Test
    public void createCrew_ShouldReturnForbiddenWhenUserIsAdmin() throws Exception {
        setUpMockAuthentication(Role.ADMIN);

        mockMvc.perform(post("/crews")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(crewDTO))
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void createCrew_ShouldReturnForbiddenWhenUserIsUser() throws Exception {
        setUpMockAuthentication(Role.USER);

        mockMvc.perform(post("/crews")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(crewDTO))
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void createCrew_ShouldReturnForbiddenWhenTokenIsInvalid() throws Exception {
        mockMvc.perform(post("/crews")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(crewDTO))
                        .header("Authorization", "Bearer " + "invalidToken"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void createCrew_ShouldReturnForbiddenWhenTokenIsMissing() throws Exception {
        mockMvc.perform(post("/crews")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(crewDTO)))
                .andExpect(status().isForbidden());
    }
    // getCrew
    @Test
    public void getCrew_ShouldReturnOkWhenUserIsDispatcher() throws Exception {
        setUpMockAuthentication(Role.DISPATCHER);

        mockMvc.perform(get("/crews/{id}", crewId)
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());
    }

    @Test
    public void getCrew_ShouldReturnForbiddenWhenUserIsAdmin() throws Exception {
        setUpMockAuthentication(Role.ADMIN);

        mockMvc.perform(get("/crews/{id}", crewId)
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getCrew_ShouldReturnOkForbiddenWhenUserIsUser() throws Exception {
        setUpMockAuthentication(Role.USER);

        mockMvc.perform(get("/crews/{id}", crewId)
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getCrew_ShouldReturnForbiddenWhenTokenIsInvalid() throws Exception {
        mockMvc.perform(get("/crews/{id}", crewId)
                        .header("Authorization", "Bearer " + "invalidToken"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getCrew_ShouldReturnForbiddenWhenTokenIsMissing() throws Exception {
        mockMvc.perform(get("/crews/{id}", crewId))
                .andExpect(status().isForbidden());
    }
    // updateCrew
    @Test
    public void updateCrew_ShouldReturnOKWhenUserIsDispatcher() throws Exception {
        setUpMockAuthentication(Role.DISPATCHER);

        mockMvc.perform(put("/crews/{id}", crewId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(crewDTO))
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());
    }

    @Test
    public void updateCrew_ShouldReturnForbiddenWhenUserIsAdmin() throws Exception {
        setUpMockAuthentication(Role.ADMIN);

        mockMvc.perform(put("/crews/{id}", crewId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(crewDTO))
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void updateCrew_ShouldReturnForbiddenWhenUserIsUser() throws Exception {
        setUpMockAuthentication(Role.USER);

        mockMvc.perform(put("/crews/{id}", crewId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(crewDTO))
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void updateCrew_ShouldReturnForbiddenWhenTokenIsInvalid() throws Exception {
        mockMvc.perform(put("/crews/{id}", crewId)
                        .header("Authorization", "Bearer " + "invalidToken"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void updateCrew_ShouldReturnForbiddenWhenTokenIsMissing() throws Exception {
        mockMvc.perform(put("/crews/{id}", crewId))
                .andExpect(status().isForbidden());
    }
    // changeLocation
    @Test
    public void changeLocation_ShouldReturnOkWhenUserIsDispatcher() throws Exception {
        setUpMockAuthentication(Role.DISPATCHER);

        mockMvc.perform(patch("/crews/{id}", crewId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(changeLocationCrewDTO))
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());
    }

    @Test
    public void changeLocation_ShouldReturnForbiddenWhenUserIsAdmin() throws Exception {
        setUpMockAuthentication(Role.ADMIN);

        mockMvc.perform(patch("/crews/{id}", crewId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(changeLocationCrewDTO))
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void changeLocation_ShouldReturnForbiddenWhenUserIsUser() throws Exception {
        setUpMockAuthentication(Role.USER);

        mockMvc.perform(patch("/crews/{id}", crewId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(changeLocationCrewDTO))
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void changeLocation_ShouldReturnForbiddenWhenTokenIsInvalid() throws Exception {
        mockMvc.perform(patch("/crews/{id}", crewId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(changeLocationCrewDTO))
                        .header("Authorization", "Bearer " + "invalidToken"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void changeLocation_ShouldReturnForbiddenWhenTokenIsMissing() throws Exception {
        mockMvc.perform(patch("/crews/{id}", crewId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(changeLocationCrewDTO)))
                .andExpect(status().isForbidden());
    }
    // deleteCrew
    @Test
    public void deleteCrew_ShouldReturnOkWhenUserIsDispatcher() throws Exception {
        setUpMockAuthentication(Role.DISPATCHER);

        mockMvc.perform(delete("/crews/{id}", crewId)
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCrew_ShouldReturnForbiddenWhenUserIsAdmin() throws Exception {
        setUpMockAuthentication(Role.ADMIN);

        mockMvc.perform(delete("/crews/{id}", crewId)
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteCrew_ShouldReturnForbiddenWhenUserIsUser() throws Exception {
        setUpMockAuthentication(Role.USER);

        mockMvc.perform(delete("/crews/{id}", crewId)
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteCrew_ShouldReturnForbiddenWhenTokenIsInvalid() throws Exception {
        mockMvc.perform(delete("/crews/{id}", crewId)
                        .header("Authorization", "Bearer " + "invalidToken"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteCrew_ShouldReturnForbiddenWhenTokenIsMissing() throws Exception {
        mockMvc.perform(delete("/crews/{id}", crewId))
                .andExpect(status().isForbidden());
    }
}
