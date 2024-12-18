package rapidaid.backend_api.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rapidaid.backend_api.models.DTOs.ChangePasswordDTO;
import rapidaid.backend_api.models.DTOs.UpdateUserDTO;
import rapidaid.backend_api.models.DTOs.UserDTO;
import rapidaid.backend_api.models.User;
import rapidaid.backend_api.services.UserService;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PutMapping("/change-password")
    public void changePassword(@RequestBody ChangePasswordDTO changePasswordDTO){
        userService.changePassword(changePasswordDTO);
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable String id){
        return userService.getUserById(id);
    }

    @PutMapping("/users/{id}")
    public UserDTO updateUser(@PathVariable String id, @RequestBody UpdateUserDTO updateUserDTO){
        return userService.updateUser(id, updateUserDTO);
    }

    @DeleteMapping("/users/{id}")
    public Boolean deleteUser(@PathVariable String id){
        return userService.deleteUser(id);
    }
}