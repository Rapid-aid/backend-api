package rapidaid.backend_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rapidaid.backend_api.models.DTOs.CrewDTO;
import rapidaid.backend_api.services.CrewService;

import java.util.List;

@RestController
public class CrewController {
    private final CrewService crewService;

    @Autowired
    public CrewController(CrewService crewService){
        this.crewService = crewService;
    }

    @GetMapping("/crews")
    public List<CrewDTO> getAllCrews(){
        return crewService.getAllCrews();
    }

    @PostMapping("/crews")
    public CrewDTO createCrew(@RequestBody CrewDTO crewDTO){
        return crewService.createCrew(crewDTO);
    }

    @GetMapping("/crews/{id}")
    public CrewDTO getCrew(@PathVariable String id){
        return crewService.getCrewById(id);
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
    public Boolean deleteCrew(@PathVariable String id){
        return crewService.deleteCrew(id);
    }
}