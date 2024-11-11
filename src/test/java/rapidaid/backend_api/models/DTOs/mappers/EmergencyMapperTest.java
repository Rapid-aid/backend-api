package rapidaid.backend_api.models.DTOs.mappers;

import org.junit.jupiter.api.Test;
import rapidaid.backend_api.models.DTOs.CreateEmergencyDTO;
import rapidaid.backend_api.models.DTOs.EmergencyDTO;
import rapidaid.backend_api.models.Emergency;
import rapidaid.backend_api.models.enums.PriorityLevel;
import rapidaid.backend_api.models.enums.Status;
import rapidaid.backend_api.models.enums.Type;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EmergencyMapperTest {
    @Test
    void testMapCreateEmergencyDTOToEmergency() {
        CreateEmergencyDTO createEmergencyDTO = CreateEmergencyDTO.builder()
                .numberOfPeople(1)
                .type(Type.FIRE)
                .description("testDescription")
                .latitude(1.0)
                .longitude(1.0).build();

        Emergency emergency = EmergencyMapper.mapToEmergency(createEmergencyDTO);

        assertNotNull(emergency);
        assertEquals(createEmergencyDTO.getNumberOfPeople(), emergency.getNumberOfPeople());
        assertEquals(createEmergencyDTO.getType(), emergency.getType());
        assertEquals(createEmergencyDTO.getDescription(), emergency.getDescription());
        assertEquals(createEmergencyDTO.getLatitude(), emergency.getLatitude());
        assertEquals(createEmergencyDTO.getLongitude(), emergency.getLongitude());
    }
    @Test
    void testMapEmergencyToEmergencyDTO() {
        Emergency emergency = Emergency.builder()
                .numberOfPeople(10)
                .type(Type.FIRE)
                .status(Status.PENDING)
                .description("testDescription")
                .priorityLevel(PriorityLevel.CRITICAL)
                .latitude(1.0)
                .longitude(1.0).build();

        EmergencyDTO emergencyDTO = EmergencyMapper.mapToEmergencyDTO(emergency);

        assertNotNull(emergencyDTO);
        assertEquals(emergency.getNumberOfPeople(), emergencyDTO.getNumberOfPeople());
        assertEquals(emergency.getType(), emergencyDTO.getType());
        assertEquals(emergency.getStatus(), emergencyDTO.getStatus());
        assertEquals(emergency.getDescription(), emergencyDTO.getDescription());
        assertEquals(emergency.getPriorityLevel(), emergencyDTO.getPriorityLevel());
        assertEquals(emergency.getLatitude(), emergencyDTO.getLatitude());
        assertEquals(emergency.getLongitude(), emergencyDTO.getLongitude());
    }

    @Test
    void testMapEmergencyDTOToEmergency() {
        EmergencyDTO emergencyDTO = EmergencyDTO.builder()
                .numberOfPeople(10)
                .type(Type.EARTHQUAKE)
                .status(Status.PENDING)
                .description("testDescription")
                .priorityLevel(PriorityLevel.CRITICAL)
                .latitude(1.0)
                .longitude(1.0).build();

        Emergency emergency = EmergencyMapper.mapToEmergency(emergencyDTO);

        assertNotNull(emergency);
        assertEquals(emergencyDTO.getNumberOfPeople(), emergency.getNumberOfPeople());
        assertEquals(emergencyDTO.getType(), emergency.getType());
        assertEquals(emergencyDTO.getStatus(), emergency.getStatus());
        assertEquals(emergencyDTO.getDescription(), emergency.getDescription());
        assertEquals(emergencyDTO.getPriorityLevel(), emergency.getPriorityLevel());
        assertEquals(emergencyDTO.getLatitude(), emergency.getLatitude());
        assertEquals(emergencyDTO.getLongitude(), emergency.getLongitude());
    }
}
