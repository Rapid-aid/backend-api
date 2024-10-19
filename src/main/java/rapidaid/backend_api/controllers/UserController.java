package rapidaid.backend_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rapidaid.backend_api.services.UserService;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getAllUsers(){
        return "getAllUsers";
    }

    @PostMapping("/users")
    public String createUser(){
        return "createUser";
    }

    @GetMapping("/users/{id}")
    public String getUser(@PathVariable Integer id){
        return "getUser " + id;
    }

    @PutMapping("/users/{id}")
    public String updateUser(@PathVariable Integer id){
        return "updateUser " + id;
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Integer id){
        return "deleteUser " + id;
    }
}