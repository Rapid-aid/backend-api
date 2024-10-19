package rapidaid.backend_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rapidaid.backend_api.services.EmergenceService;

@RestController
public class EmergenceController {
    private EmergenceService emergenceService;

    @Autowired
    public EmergenceController(EmergenceService emergenceService){
        this.emergenceService = emergenceService;
    }

    @GetMapping("/emergencies")
    public String getAllEmergencies(){
        return "getAllEmergencies";
    }

    @PostMapping("/emergencies")
    public String createEmergence(){
        return "createEmergence";
    }

    @GetMapping("/emergencies/{id}")
    public String getEmergence(@PathVariable Integer id){
        return "getEmergence " + id;
    }

    @PutMapping("/emergencies/{id}")
    public String updateEmergence(@PathVariable Integer id){
        return "updateEmergence " + id;
    }

    @DeleteMapping("/emergencies/{id}")
    public String deleteEmergence(@PathVariable Integer id){
        return "deleteEmergence " + id;
    }
}