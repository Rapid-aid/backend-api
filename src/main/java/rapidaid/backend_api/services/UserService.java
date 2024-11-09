package rapidaid.backend_api.services;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rapidaid.backend_api.models.DTOs.ChangePasswordDTO;
import rapidaid.backend_api.models.DTOs.UpdateUserDTO;
import rapidaid.backend_api.models.DTOs.UserDTO;
import rapidaid.backend_api.models.DTOs.mappers.UserMapper;
import rapidaid.backend_api.models.User;
import rapidaid.backend_api.repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDTO updateUser(String id, UpdateUserDTO updateUserDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (updateUserDTO.getFirstName() != null) {
            user.setFirstName(updateUserDTO.getFirstName());
        }
        if (updateUserDTO.getLastName() != null) {
            user.setLastName(updateUserDTO.getLastName());
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
                    user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
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