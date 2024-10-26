package rapidaid.backend_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rapidaid.backend_api.models.DTOs.UserDTO;
import rapidaid.backend_api.models.User;
import rapidaid.backend_api.services.UserService;

import java.util.List;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody UserDTO userDTO) {
        return userService.registerUser(userDTO);
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable String id){
        return userService.getUserById(id);
    }

    @PutMapping("/users/{id}")
    public String updateUser(@PathVariable String id){
        return "updateUser " + id;
    }

    @DeleteMapping("/users/{id}")
    public Boolean deleteUser(@PathVariable String id){
        return userService.deleteUser(id);
    }
}