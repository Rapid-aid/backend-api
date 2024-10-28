package rapidaid.backend_api.models.DTOs.mappers;

import rapidaid.backend_api.models.DTOs.CreateUserDTO;
import rapidaid.backend_api.models.DTOs.UserDTO;
import rapidaid.backend_api.models.User;

public class UserMapper {
    public static User mapToUser(CreateUserDTO createUserDTO) {
        return User.builder()
                .username(createUserDTO.getUsername())
                .email(createUserDTO.getEmail())
                .password(createUserDTO.getPassword())
                .address(createUserDTO.getAddress())
                .phoneNumber(createUserDTO.getPhoneNumber())
                .build();
    }
    public static UserDTO mapToUserDTO(User user) {
        return UserDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
