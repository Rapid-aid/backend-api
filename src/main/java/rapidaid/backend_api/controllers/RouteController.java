package rapidaid.backend_api.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class RouteController {
    @GetMapping("/routes")
    public String getAllRouters(){
        return "getAllRouters";
    }

    @GetMapping("/routes/{id}")
    public String getRoute(@PathVariable Integer id){
        return "getRoute " + id;
    }

    @PatchMapping("/routes/{id}")
    public String updateRoute(@PathVariable Integer id){
        return "updateRoute " + id;
    }
}