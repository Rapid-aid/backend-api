package rapidaid.backend_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rapidaid.backend_api.models.ChangePassword;
import rapidaid.backend_api.models.DTOs.ChangePasswordDTO;
import rapidaid.backend_api.models.DTOs.CreateUserDTO;
import rapidaid.backend_api.models.DTOs.UpdateUserDTO;
import rapidaid.backend_api.models.DTOs.UserDTO;
import rapidaid.backend_api.models.DTOs.mappers.ChangePasswordMapper;
import rapidaid.backend_api.models.DTOs.mappers.UserMapper;
import rapidaid.backend_api.models.User;
import rapidaid.backend_api.services.UserService;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping(value = "/register")
    public UserDTO createUser(@RequestBody CreateUserDTO createUserDTO){
        return userService.registerUser(UserMapper.mapToUser(createUserDTO));
    }
    @PutMapping("/change-password")
    public void changePassword(@RequestBody ChangePasswordDTO changePasswordDTO){
        userService.changePassword(ChangePasswordMapper.mapToChangePassword(changePasswordDTO));
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