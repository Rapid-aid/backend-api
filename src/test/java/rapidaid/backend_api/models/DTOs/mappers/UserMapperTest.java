package rapidaid.backend_api.models.DTOs.mappers;

import org.junit.jupiter.api.Test;
import rapidaid.backend_api.models.DTOs.CreateUserDTO;
import rapidaid.backend_api.models.DTOs.UserDTO;
import rapidaid.backend_api.models.User;
import rapidaid.backend_api.models.enums.Role;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserMapperTest {
    @Test
    void testMapCreateUserDTOToUser() {
        CreateUserDTO createUserDTO = CreateUserDTO.builder()
                .firstName("firstName")
                .lastName("lastName")
                .address("address")
                .phoneNumber("123456789")
                .email("test@example.com").build();

        User user = UserMapper.mapToUser(createUserDTO);

        assertNotNull(user);
        assertEquals(user.getFirstName(), createUserDTO.getFirstName());
        assertEquals(user.getLastName(), createUserDTO.getLastName());
        assertEquals(user.getAddress(), createUserDTO.getAddress());
        assertEquals(user.getPhoneNumber(), createUserDTO.getPhoneNumber());
        assertEquals(user.getEmail(), createUserDTO.getEmail());
    }
    @Test
    void testMapUserToUserDTO() {
        User user = User.builder()
                .firstName("firstName")
                .lastName("lastName")
                .email("test@example.com")
                .role(Role.RESPONDER)
                .build();

        UserDTO userDTO = UserMapper.mapToUserDTO(user);

        assertNotNull(userDTO);
        assertEquals(userDTO.getFirstName(), user.getFirstName());
        assertEquals(userDTO.getLastName(), user.getLastName());
        assertEquals(userDTO.getEmail(), user.getEmail());
        assertEquals(userDTO.getRole(), user.getRole());
    }
}
