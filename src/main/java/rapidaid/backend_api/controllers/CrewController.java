package rapidaid.backend_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rapidaid.backend_api.services.CrewService;

@RestController
public class CrewController {
    private CrewService crewService;

    @Autowired
    public CrewController(CrewService crewService){
        this.crewService = crewService;
    }

    @GetMapping("/crews")
    public String getAllCrews(){
        return "getAllCrews";
    }

    @PostMapping("/crews")
    public String createCrew(){
        return "createCrew";
    }

    @GetMapping("/crews/{id}")
    public String getCrew(@PathVariable Integer id){
        return "getCrew " + id;
    }

    @PutMapping("/crews/{id}")
    public String updateCrew(@PathVariable Integer id){
        return "updateCrew " + id;
    }
    @PatchMapping("/crews/{id}")
    public String patchCrew(@PathVariable Integer id){
        return "patchCrew " + id;
    }

    @DeleteMapping("/crews/{id}")
    public String deleteCrew(@PathVariable Integer id){
        return "deleteCrew " + id;
    }
}