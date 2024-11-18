package rapidaid.backend_api.controllers.Route;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import rapidaid.backend_api.controllers.AbstractMockMvcTest;
import rapidaid.backend_api.models.enums.Role;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RouteControllerTest extends AbstractMockMvcTest {
    String routeId = "1";

    // getAllRouters
    @Test
    public void getAllRouters_ShouldReturnOkWhenUserIsDispatcher() throws Exception {
        setUpMockAuthentication(Role.DISPATCHER);

        mockMvc.perform(get("/routes")
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllRouters_ShouldReturnForbiddenWhenUserIsUser() throws Exception {
        setUpMockAuthentication(Role.USER);

        mockMvc.perform(get("/routes")
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAllRouters_ShouldReturnForbiddenWhenUserIsAdmin() throws Exception {
        setUpMockAuthentication(Role.ADMIN);

        mockMvc.perform(get("/routes")
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAllRouters_ShouldReturnForbiddenWhenTokenIsInvalid() throws Exception {
        mockMvc.perform(get("/routes")
                        .header("Authorization", "Bearer " + "invalidToken"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAllRouters_ShouldReturnForbiddenWhenTokenIsMissing() throws Exception {
        mockMvc.perform(get("/routes"))
                .andExpect(status().isForbidden());
    }
    // getRoute
    @Test
    public void getRoute_ShouldReturnOkWhenUserIsDispatcher() throws Exception {
        setUpMockAuthentication(Role.DISPATCHER);

        mockMvc.perform(get("/routes/{id}", routeId)
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());
    }

    @Test
    public void getRoute_ShouldReturnForbiddenWhenUserIsAdmin() throws Exception {
        setUpMockAuthentication(Role.ADMIN);

        mockMvc.perform(get("/routes/{id}", routeId)
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getRoute_ShouldReturnOkForbiddenWhenUserIsUser() throws Exception {
        setUpMockAuthentication(Role.USER);

        mockMvc.perform(get("/routes/{id}", routeId)
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getRoute_ShouldReturnForbiddenWhenTokenIsInvalid() throws Exception {
        mockMvc.perform(get("/routes/{id}", routeId)
                        .header("Authorization", "Bearer " + "invalidToken"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getRoute_ShouldReturnForbiddenWhenTokenIsMissing() throws Exception {
        mockMvc.perform(get("/routes/{id}", routeId))
                .andExpect(status().isForbidden());
    }

    // updateRoute
    @Test
    public void updateRoute_ShouldReturnOkWhenUserIsDispatcher() throws Exception {
        setUpMockAuthentication(Role.DISPATCHER);

        mockMvc.perform(patch("/routes/{id}", routeId)
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isOk());
    }
    @Test
    public void updateRoute_ShouldReturnForbiddenWhenUserIsAdmin() throws Exception {
        setUpMockAuthentication(Role.ADMIN);

        mockMvc.perform(patch("/routes/{id}", routeId)
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void updateRoute_ShouldReturnForbiddenWhenUserIsUser() throws Exception {
        setUpMockAuthentication(Role.USER);

        mockMvc.perform(patch("/routes/{id}", routeId)
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + mockToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void updateRoute_ShouldReturnForbiddenWhenTokenIsInvalid() throws Exception {
        mockMvc.perform(patch("/routes/{id}", routeId)
                        .contentType("application/json")
                        .header("Authorization", "Bearer " + "invalidToken"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void updateRoute_ShouldReturnForbiddenThenTokenIsMissing() throws Exception {
        mockMvc.perform(patch("/routes/{id}", routeId)
                        .contentType("application/json"))
                .andExpect(status().isForbidden());
    }
}
