package rapidaid.backend_api.models.DTOs.mappers;

import lombok.AllArgsConstructor;
import rapidaid.backend_api.models.DTOs.CreateUserDTO;
import rapidaid.backend_api.models.DTOs.UserDTO;
import rapidaid.backend_api.models.User;

@AllArgsConstructor
public class UserMapper {
    public static User mapToUser(CreateUserDTO createUserDTO) {
        return User.builder()
                .firstName(createUserDTO.getFirstName())
                .lastName(createUserDTO.getLastName())
                .email(createUserDTO.getEmail())
                .address(createUserDTO.getAddress())
                .phoneNumber(createUserDTO.getPhoneNumber())
                .build();
    }
    public static UserDTO mapToUserDTO(User user) {
        return UserDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
