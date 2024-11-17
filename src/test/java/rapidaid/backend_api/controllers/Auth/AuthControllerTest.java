package rapidaid.backend_api.controllers.Auth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import rapidaid.backend_api.controllers.AbstractMockMvcTest;
import rapidaid.backend_api.models.DTOs.CreateUserDTO;
import rapidaid.backend_api.models.DTOs.UserCredentials;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest extends AbstractMockMvcTest {
    CreateUserDTO createUserDTO = CreateUserDTO.builder().firstName("testFirstName").lastName("testLastName").password("testPassword").email("test@email.com").address("testAddress").phoneNumber("123456789").build();
    UserCredentials userCredentials = UserCredentials.builder().email("testEmail").password("testPassword").build();

    @Test
    public void register_ShouldReturnOk() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createUserDTO)))
                .andExpect(status().isOk());
    }
}
