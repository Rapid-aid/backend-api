package rapidaid.backend_api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rapidaid.backend_api.models.DTOs.CreateEmergencyDTO;
import rapidaid.backend_api.models.DTOs.EmergencyDTO;
import rapidaid.backend_api.models.Emergency;
import rapidaid.backend_api.repositories.EmergenceRepository;
import rapidaid.backend_api.websocket.service.WebSocketService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class EmergencyServiceTest {
    @Mock
    private EmergenceRepository emergenceRepository;
    @Mock
    private WebSocketService webSocketService;
    @InjectMocks
    private EmergenceService emergenceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createEmergence_whenEmergenciesNotExist_thenShouldReturnEmergencyDTO() {
        CreateEmergencyDTO createEmergencyDTO = CreateEmergencyDTO.builder()
                .location("testLocation")
                .description("testDescription")
                .numberOfPeople(5).build();

        EmergencyDTO emergencyDTO = emergenceService.createEmergence(createEmergencyDTO);

        assertNotNull(emergencyDTO);
        assertEquals(createEmergencyDTO.getLocation(), emergencyDTO.getLocation());
        assertEquals(createEmergencyDTO.getDescription(), emergencyDTO.getDescription());
        assertEquals(createEmergencyDTO.getNumberOfPeople(), emergencyDTO.getNumberOfPeople());

        verify(emergenceRepository, times(1)).save(any(Emergency.class));
    }
}
