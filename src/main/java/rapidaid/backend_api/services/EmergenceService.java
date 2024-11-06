package rapidaid.backend_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rapidaid.backend_api.models.DTOs.CreateEmergencyDTO;
import rapidaid.backend_api.models.DTOs.EmergencyDTO;
import rapidaid.backend_api.models.DTOs.mappers.EmergencyMapper;
import rapidaid.backend_api.models.Emergency;
import rapidaid.backend_api.models.enums.Status;
import rapidaid.backend_api.repositories.EmergenceRepository;

import java.util.List;

@Service
public class EmergenceService {
    private final WebSocketService webSocketService;
    private final EmergenceRepository emergenceRepository;

    @Autowired
    public EmergenceService(WebSocketService webSocketService, EmergenceRepository emergenceRepository) {
        this.webSocketService = webSocketService;
        this.emergenceRepository = emergenceRepository;
    }

    public List<EmergencyDTO> getAllEmergencies() {
        return emergenceRepository.findAll().stream()
                .map(EmergencyMapper::mapToEmergencyDTO)
                .toList();
    }

    public EmergencyDTO createEmergence(CreateEmergencyDTO createEmergencyDTO) {
        Emergency emergency = EmergencyMapper.mapToEmergency(createEmergencyDTO);
        emergency.setStatus(Status.PENDING);

        emergenceRepository.save(emergency);
        webSocketService.notify(emergency.getId());

        return EmergencyMapper.mapToEmergencyDTO(emergency);
    }

    public EmergencyDTO getEmergence(String id) {
        return EmergencyMapper.mapToEmergencyDTO(emergenceRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Emergency not found")));
    }

    public EmergencyDTO updateEmergence(String id, EmergencyDTO emergencyDTO) {
        Emergency emergency = emergenceRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Emergency not found"));
        if (emergencyDTO.getLocation() != null) {
            emergency.setLocation(emergencyDTO.getLocation());
        }
        if (emergencyDTO.getDescription() != null) {
            emergency.setDescription(emergencyDTO.getDescription());
        }
        if (emergencyDTO.getNumberOfPeople() != null) {
            emergency.setNumberOfPeople(emergencyDTO.getNumberOfPeople());
        }
        if (emergencyDTO.getStatus() != null) {
            emergency.setStatus(emergencyDTO.getStatus());
        }
        emergenceRepository.save(emergency);
        return EmergencyMapper.mapToEmergencyDTO(emergency);
    }

    public Boolean deleteEmergence(String id) {
        if (emergenceRepository.existsById(id)) {
            emergenceRepository.deleteById(id);
            return true;
        }
        throw new IllegalArgumentException("Emergency not found");
    }
}
