package rapidaid.backend_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rapidaid.backend_api.models.DTOs.CreateEmergencyDTO;
import rapidaid.backend_api.models.DTOs.EmergencyDTO;
import rapidaid.backend_api.models.DTOs.mappers.EmergencyMapper;
import rapidaid.backend_api.models.Emergency;
import rapidaid.backend_api.models.enums.Status;
import rapidaid.backend_api.repositories.EmergenceRepository;
import rapidaid.backend_api.services.websocket.WebSocketService;

@Service
public class EmergenceService {
    private final WebSocketService webSocketService;
    private final EmergenceRepository emergenceRepository;

    @Autowired
    public EmergenceService(WebSocketService webSocketService, EmergenceRepository emergenceRepository) {
        this.webSocketService = webSocketService;
        this.emergenceRepository = emergenceRepository;
    }

    public EmergencyDTO createEmergence(CreateEmergencyDTO createEmergencyDTO) {
        Emergency emergency = Emergency.builder()
                .location(createEmergencyDTO.getLocation())
                .description(createEmergencyDTO.getDescription())
                .numberOfPeople(createEmergencyDTO.getNumberOfPeople())
                .status(Status.PENDING)
                .build();

        emergenceRepository.save(emergency);
        webSocketService.notify(emergency.getId());

        return EmergencyMapper.mapToEmergencyDTO(emergency);
    }
}
