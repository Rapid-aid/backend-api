package rapidaid.backend_api.services;


import org.springframework.stereotype.Service;
import rapidaid.backend_api.models.DTOs.ChangePasswordDTO;
import rapidaid.backend_api.models.DTOs.CreateUserDTO;
import rapidaid.backend_api.models.DTOs.UpdateUserDTO;
import rapidaid.backend_api.models.DTOs.UserDTO;
import rapidaid.backend_api.models.DTOs.mappers.UserMapper;
import rapidaid.backend_api.models.User;
import rapidaid.backend_api.models.enums.Role;
import rapidaid.backend_api.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDTO registerUser(CreateUserDTO createUserDTO) {
        User user = UserMapper.mapToUser(createUserDTO);
        if (userRepository.existsByEmail(user.getEmail()) || userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Any account with email or name already exists");
        }

        userRepository.save(User.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .role(Role.RESPONDER).build());
        return UserMapper.mapToUserDTO(user);
    }

    public UserDTO updateUser(String id, UpdateUserDTO updateUserDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (updateUserDTO.getUsername() != null) {
            user.setUsername(updateUserDTO.getUsername());
        }
        if (updateUserDTO.getEmail() != null) {
            user.setEmail(updateUserDTO.getEmail());
        }
        if (updateUserDTO.getAddress() != null) {
            user.setAddress(updateUserDTO.getAddress());
        }
        if (updateUserDTO.getPhoneNumber() != null) {
            user.setPhoneNumber(updateUserDTO.getPhoneNumber());
        }
        userRepository.save(user);
        return UserMapper.mapToUserDTO(user);
    }

    public void changePassword(ChangePasswordDTO changePasswordDTO) {
        userRepository.findByEmail(changePasswordDTO.getEmail())
                .ifPresentOrElse(user -> {
                    user.setPassword(changePasswordDTO.getNewPassword());
                    userRepository.save(user);
                }, () -> {
                    throw new IllegalArgumentException("User not found");
                });
    }

    public Boolean deleteUser(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}