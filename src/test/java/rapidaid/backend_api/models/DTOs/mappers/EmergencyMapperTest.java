package rapidaid.backend_api.models.DTOs.mappers;

import org.junit.jupiter.api.Test;
import rapidaid.backend_api.models.DTOs.CreateEmergencyDTO;
import rapidaid.backend_api.models.DTOs.EmergencyDTO;
import rapidaid.backend_api.models.Emergency;
import rapidaid.backend_api.models.enums.PriorityLevel;
import rapidaid.backend_api.models.enums.Status;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EmergencyMapperTest {
    @Test
    void testMapCreateEmergencyDTOToEmergency() {
        CreateEmergencyDTO createEmergencyDTO = CreateEmergencyDTO.builder()
                .description("testDescription")
                .location("testLocation")
                .numberOfPeople(1)
                .build();

        Emergency emergency = EmergencyMapper.mapToEmergency(createEmergencyDTO);

        assertNotNull(emergency);
        assertEquals(createEmergencyDTO.getDescription(), emergency.getDescription());
        assertEquals(createEmergencyDTO.getLocation(), emergency.getLocation());
        assertEquals(createEmergencyDTO.getNumberOfPeople(), emergency.getNumberOfPeople());
    }
    @Test
    void testMapEmergencyToEmergencyDTO() {
        Emergency emergency = Emergency.builder()
                .description("testDescription")
                .location("testLocation")
                .numberOfPeople(10)
                .priorityLevel(PriorityLevel.CRITICAL)
                .status(Status.PENDING)
                .build();

        EmergencyDTO emergencyDTO = EmergencyMapper.mapToEmergencyDTO(emergency);

        assertNotNull(emergencyDTO);
        assertEquals(emergency.getDescription(), emergencyDTO.getDescription());
        assertEquals(emergency.getLocation(), emergencyDTO.getLocation());
        assertEquals(emergency.getNumberOfPeople(), emergencyDTO.getNumberOfPeople());
        assertEquals(emergency.getPriorityLevel(), emergencyDTO.getPriorityLevel());
        assertEquals(emergency.getStatus(), emergencyDTO.getStatus());
    }

    @Test
    void testMapEmergencyDTOToEmergency() {
        EmergencyDTO emergencyDTO = EmergencyDTO.builder()
                .description("testDescription")
                .location("testLocation")
                .numberOfPeople(10)
                .priorityLevel(PriorityLevel.CRITICAL)
                .status(Status.PENDING)
                .build();

        Emergency emergency = EmergencyMapper.mapToEmergency(emergencyDTO);

        assertNotNull(emergency);
        assertEquals(emergencyDTO.getDescription(), emergency.getDescription());
        assertEquals(emergencyDTO.getLocation(), emergency.getLocation());
        assertEquals(emergencyDTO.getNumberOfPeople(), emergency.getNumberOfPeople());
        assertEquals(emergencyDTO.getPriorityLevel(), emergency.getPriorityLevel());
        assertEquals(emergencyDTO.getStatus(), emergency.getStatus());
    }
}
