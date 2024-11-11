package rapidaid.backend_api.models.DTOs.mappers;

import rapidaid.backend_api.models.DTOs.CreateEmergencyDTO;
import rapidaid.backend_api.models.DTOs.EmergencyDTO;
import rapidaid.backend_api.models.Emergency;

public class EmergencyMapper {

    public static Emergency mapToEmergency(CreateEmergencyDTO emergencyDTO) {
        return Emergency.builder()
                .numberOfPeople(emergencyDTO.getNumberOfPeople())
                .type(emergencyDTO.getType())
                .description(emergencyDTO.getDescription())
                .latitude(emergencyDTO.getLatitude())
                .longitude(emergencyDTO.getLongitude())
                .build();
    }
    public static EmergencyDTO mapToEmergencyDTO(Emergency emergency) {
        return EmergencyDTO.builder()
                .numberOfPeople(emergency.getNumberOfPeople())
                .type(emergency.getType())
                .status(emergency.getStatus())
                .description(emergency.getDescription())
                .priorityLevel(emergency.getPriorityLevel())
                .longitude(emergency.getLongitude())
                .latitude(emergency.getLatitude())
                .build();
    }
    public static Emergency mapToEmergency(EmergencyDTO emergencyDTO) {
        return Emergency.builder()
                .numberOfPeople(emergencyDTO.getNumberOfPeople())
                .type(emergencyDTO.getType())
                .status(emergencyDTO.getStatus())
                .description(emergencyDTO.getDescription())
                .priorityLevel(emergencyDTO.getPriorityLevel())
                .latitude(emergencyDTO.getLatitude())
                .longitude(emergencyDTO.getLongitude())
                .build();
    }
}
