package rapidaid.backend_api.models.DTOs.mappers;

import rapidaid.backend_api.models.DTOs.CreateEmergencyDTO;
import rapidaid.backend_api.models.DTOs.EmergencyDTO;
import rapidaid.backend_api.models.Emergency;

public class EmergencyMapper {

    public static Emergency mapToEmergency(CreateEmergencyDTO emergencyDTO) {
        return Emergency.builder()
                .description(emergencyDTO.getDescription())
                .location(emergencyDTO.getLocation())
                .numberOfPeople(emergencyDTO.getNumberOfPeople())
                .build();
    }
    public static EmergencyDTO mapToEmergencyDTO(Emergency emergency) {
        return EmergencyDTO.builder()
                .description(emergency.getDescription())
                .location(emergency.getLocation())
                .numberOfPeople(emergency.getNumberOfPeople())
                .priorityLevel(emergency.getPriorityLevel())
                .status(emergency.getStatus())
                .build();
    }
    public static Emergency mapToEmergency(EmergencyDTO EmergencyDTO) {
        return Emergency.builder()
                .description(EmergencyDTO.getDescription())
                .location(EmergencyDTO.getLocation())
                .numberOfPeople(EmergencyDTO.getNumberOfPeople())
                .priorityLevel(EmergencyDTO.getPriorityLevel())
                .status(EmergencyDTO.getStatus())
                .build();
    }
}
