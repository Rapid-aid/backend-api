package rapidaid.backend_api.models.DTOs.mappers;

import rapidaid.backend_api.models.ChangePassword;
import rapidaid.backend_api.models.DTOs.ChangePasswordDTO;

public class ChangePasswordMapper {
    public static ChangePassword mapToChangePassword(ChangePasswordDTO changePasswordDTO) {
        return new ChangePassword(changePasswordDTO.getEmail(), changePasswordDTO.getNewPassword());
    }
}
