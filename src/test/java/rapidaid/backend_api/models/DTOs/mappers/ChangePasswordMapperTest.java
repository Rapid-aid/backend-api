package rapidaid.backend_api.models.DTOs.mappers;

import org.junit.jupiter.api.Test;
import rapidaid.backend_api.models.ChangePassword;
import rapidaid.backend_api.models.DTOs.ChangePasswordDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChangePasswordMapperTest {
    @Test
    public void testMapToChangePassword() {
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("test@example.com", "newPassword");

        ChangePassword changePassword = ChangePasswordMapper.mapToChangePassword(changePasswordDTO);

        assertNotNull(changePassword);
        assertEquals(changePassword.getEmail(), changePasswordDTO.getEmail());
    }
}
