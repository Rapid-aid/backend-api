package rapidaid.backend_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rapidaid.backend_api.models.DTOs.CreateEmergencyDTO;
import rapidaid.backend_api.models.DTOs.EmergencyDTO;
import rapidaid.backend_api.services.EmergenceService;

import java.util.List;

@RestController
public class EmergenceController {
    private final EmergenceService emergenceService;

    @Autowired
    public EmergenceController(EmergenceService emergenceService){
        this.emergenceService = emergenceService;
    }

    @GetMapping("/emergencies")
    public List<EmergencyDTO> getAllEmergencies(){
        return emergenceService.getAllEmergencies();
    }

    @PostMapping("/emergencies")
    public EmergencyDTO createEmergence(@RequestBody CreateEmergencyDTO createEmergencyDTO){
        return emergenceService.createEmergence(createEmergencyDTO);
    }

    @GetMapping("/emergencies/{id}")
    public EmergencyDTO getEmergence(@PathVariable String id){
        return emergenceService.getEmergence(id);
    }

    @PutMapping("/emergencies/{id}")
    public EmergencyDTO updateEmergence(@PathVariable String id, @RequestBody EmergencyDTO emergencyDTO){
        return emergenceService.updateEmergence(id, emergencyDTO);
    }

    @DeleteMapping("/emergencies/{id}")
    public Boolean deleteEmergence(@PathVariable String id){
        return emergenceService.deleteEmergence(id);
    }
}