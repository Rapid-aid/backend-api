package rapidaid.backend_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rapidaid.backend_api.models.DTOs.CreateEmergencyDTO;
import rapidaid.backend_api.models.DTOs.EmergencyDTO;
import rapidaid.backend_api.services.EmergenceService;

@RestController
public class EmergenceController {
    private final EmergenceService emergenceService;

    @Autowired
    public EmergenceController(EmergenceService emergenceService){
        this.emergenceService = emergenceService;
    }

    @GetMapping("/emergencies")
    public String getAllEmergencies(){
        return "getAllEmergencies";
    }

    @PostMapping("/emergencies")
    public EmergencyDTO createEmergence(@RequestBody CreateEmergencyDTO createEmergencyDTO){
        return emergenceService.createEmergence(createEmergencyDTO);
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