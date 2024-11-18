package rapidaid.backend_api.controllers.Emergence;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import rapidaid.backend_api.controllers.AbstractMockMvcTest;
import rapidaid.backend_api.models.DTOs.CreateEmergencyDTO;
import rapidaid.backend_api.models.DTOs.EmergencyDTO;
import rapidaid.backend_api.models.enums.Role;
import rapidaid.backend_api.models.enums.Type;
import rapidaid.backend_api.services.EmergenceService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmergenceControllerTest extends AbstractMockMvcTest {
    @MockBean
    private EmergenceService emergenceService;

    CreateEmergencyDTO createEmergencyDTO = CreateEmergencyDTO.builder().numberOfPeople(1).type(Type.ACCIDENT).description("testEmergencyDescription").latitude(1.0).longitude(1.0).build();
    EmergencyDTO emergencyDTO = EmergencyDTO.builder().numberOfPeople(1).type(Type.ACCIDENT).description("testEmergencyDescription").latitude(1.0).longitude(1.0).build();
    String emergenceId = "1";

    // getAllEmergencies
    @Test
    public void getAllEmergencies_ShouldReturnOkWhenUserIsDispatcher() throws Exception {
        setUpMockAuthentication(Role.DISPATCHER);

        mockMvc.perform(get("/emergencies")
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllEmergencies_ShouldReturnForbiddenWhenUserIsUser() throws Exception {
        setUpMockAuthentication(Role.USER);

        mockMvc.perform(get("/emergencies")
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAllEmergencies_ShouldReturnForbiddenWhenUserIsAdmin() throws Exception {
        setUpMockAuthentication(Role.ADMIN);

        mockMvc.perform(get("/emergencies")
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAllEmergencies_ShouldReturnForbiddenWhenTokenIsInvalid() throws Exception {
        mockMvc.perform(get("/emergencies")
                        .header("Authorization", "Bearer " + "invalidToken"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAllEmergencies_ShouldReturnForbiddenWhenTokenIsMissing() throws Exception {
        mockMvc.perform(get("/emergencies"))
                .andExpect(status().isForbidden());
    }
    // createEmergence
    @Test
    public void createEmergence_ShouldReturnOkWhenUserIsDispatcher() throws Exception {
        setUpMockAuthentication(Role.DISPATCHER);

        mockMvc.perform(post("/emergencies")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createEmergencyDTO))
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());
    }

    @Test
    public void createEmergence_ShouldReturnForbiddenWhenUserIsAdmin() throws Exception {
        setUpMockAuthentication(Role.ADMIN);

        mockMvc.perform(post("/emergencies")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createEmergencyDTO))
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void createEmergence_ShouldReturnOkWhenUserIsUser() throws Exception {
        setUpMockAuthentication(Role.USER);

        mockMvc.perform(post("/emergencies")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createEmergencyDTO))
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());
    }

    @Test
    public void createEmergence_ShouldReturnForbiddenWhenTokenIsInvalid() throws Exception {
        mockMvc.perform(post("/emergencies")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createEmergencyDTO))
                        .header("Authorization", "Bearer " + "invalidToken"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void createEmergence_ShouldReturnForbiddenWhenTokenIsMissing() throws Exception {
        mockMvc.perform(post("/emergencies")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createEmergencyDTO)))
                .andExpect(status().isForbidden());
    }
    // getEmergence
    @Test
    public void getEmergence_ShouldReturnOkWhenUserIsDispatcher() throws Exception {
        setUpMockAuthentication(Role.DISPATCHER);

        mockMvc.perform(get("/emergencies/{id}", emergenceId)
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());
    }

    @Test
    public void getEmergence_ShouldReturnForbiddenWhenUserIsAdmin() throws Exception {
        setUpMockAuthentication(Role.ADMIN);

        mockMvc.perform(get("/emergencies/{id}", emergenceId)
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getEmergence_ShouldReturnOkForbiddenWhenUserIsUser() throws Exception {
        setUpMockAuthentication(Role.USER);

        mockMvc.perform(get("/emergencies/{id}", emergenceId)
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getEmergence_ShouldReturnForbiddenWhenTokenIsInvalid() throws Exception {
        mockMvc.perform(get("/emergencies/{id}", emergenceId)
                        .header("Authorization", "Bearer " + "invalidToken"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getEmergence_ShouldReturnForbiddenWhenTokenIsMissing() throws Exception {
        mockMvc.perform(get("/emergencies/{id}", emergenceId))
                .andExpect(status().isForbidden());
    }
    // searchEmergencies
    @Test
    public void searchEmergencies_ShouldReturnOkWhenUserIsDispatcher() throws Exception {
        setUpMockAuthentication(Role.DISPATCHER);

        mockMvc.perform(get("/emergencies/search")
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());
    }

    @Test
    public void searchEmergencies_ShouldReturnForbiddenWhenUserIsAdmin() throws Exception {
        setUpMockAuthentication(Role.ADMIN);

        mockMvc.perform(get("/emergencies/search")
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void searchEmergencies_ShouldReturnForbiddenWhenUserIsUser() throws Exception {
        setUpMockAuthentication(Role.USER);

        mockMvc.perform(get("/emergencies/search")
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void searchEmergencies_ShouldReturnForbiddenWhenTokenIsInvalid() throws Exception {
        mockMvc.perform(get("/emergencies/search")
                        .header("Authorization", "Bearer " + "invalidToken"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void searchEmergencies_ShouldReturnForbiddenWhenTokenIsMissing() throws Exception {
        mockMvc.perform(get("/emergencies/search"))
                .andExpect(status().isForbidden());
    }

    // updateEmergence
    @Test
    public void updateEmergence_ShouldReturnOKWhenUserIsDispatcher() throws Exception {
        setUpMockAuthentication(Role.DISPATCHER);

        mockMvc.perform(put("/emergencies/{id}", emergenceId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(emergencyDTO))
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());
    }

    @Test
    public void updateEmergence_ShouldReturnOkWhenUserIsUser() throws Exception {
        setUpMockAuthentication(Role.USER);

        mockMvc.perform(put("/emergencies/{id}", emergenceId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(emergencyDTO))
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());
    }

    @Test
    public void updateEmergence_ShouldReturnForbiddenWhenUserIsAdmin() throws Exception {
        setUpMockAuthentication(Role.ADMIN);

        mockMvc.perform(put("/emergencies/{id}", emergenceId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(emergencyDTO))
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void updateEmergence_ShouldReturnForbiddenWhenTokenIsInvalid() throws Exception {
        mockMvc.perform(put("/emergencies/{id}", emergenceId)
                        .header("Authorization", "Bearer " + "invalidToken"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void updateEmergence_ShouldReturnForbiddenWhenTokenIsMissing() throws Exception {
        mockMvc.perform(put("/emergencies/{id}", emergenceId))
                .andExpect(status().isForbidden());
    }
    // deleteEmergence
    @Test
    public void deleteEmergence_ShouldReturnOkWhenUserIsDispatcher() throws Exception {
        setUpMockAuthentication(Role.DISPATCHER);

        mockMvc.perform(delete("/emergencies/{id}", emergenceId)
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteEmergence_ShouldReturnForbiddenWhenUserIsAdmin() throws Exception {
        setUpMockAuthentication(Role.ADMIN);

        mockMvc.perform(delete("/emergencies/{id}", emergenceId)
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteEmergence_ShouldReturnForbiddenWhenUserIsUser() throws Exception {
        setUpMockAuthentication(Role.USER);

        mockMvc.perform(delete("/emergencies/{id}", emergenceId)
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteEmergence_ShouldReturnForbiddenWhenTokenIsInvalid() throws Exception {
        mockMvc.perform(delete("/emergencies/{id}", emergenceId)
                        .header("Authorization", "Bearer " + "invalidToken"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteEmergence_ShouldReturnForbiddenWhenTokenIsMissing() throws Exception {
        mockMvc.perform(delete("/emergencies/{id}", emergenceId))
                .andExpect(status().isForbidden());
    }
}
