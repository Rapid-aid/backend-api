package rapidaid.backend_api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rapidaid.backend_api.models.DTOs.CreateEmergencyDTO;
import rapidaid.backend_api.models.DTOs.EmergencyDTO;
import rapidaid.backend_api.models.DTOs.mappers.EmergencyMapper;
import rapidaid.backend_api.models.Emergency;
import rapidaid.backend_api.repositories.EmergenceRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    public void getAllEmergencies_whenEmergenciesExist_thenShouldReturnEmergencyDTOList() {
        EmergencyDTO emergencyDTO1 = EmergencyDTO.builder()
                .location("testLocation1")
                .description("testDescription1")
                .numberOfPeople(1).build();
        EmergencyDTO emergencyDTO2 = EmergencyDTO.builder()
                .location("testLocation2")
                .description("testDescription2")
                .numberOfPeople(2).build();

        when(emergenceRepository.findAll()).thenReturn(List.of(EmergencyMapper.mapToEmergency(emergencyDTO1), EmergencyMapper.mapToEmergency(emergencyDTO2)));

        List<EmergencyDTO> emergencyDTOList = emergenceService.getAllEmergencies();

        assertNotNull(emergencyDTOList);
        assertEquals(2, emergencyDTOList.size());
        assertEquals(emergencyDTO1, emergencyDTOList.getFirst());
        assertEquals(emergencyDTO2, emergencyDTOList.getLast());
        verify(emergenceRepository, times(1)).findAll();
    }

    @Test
    public void getAllEmergencies_whenEmergenciesNotExist_thenShouldReturnEmptyList() {
        when(emergenceRepository.findAll()).thenReturn(List.of());

        List<EmergencyDTO> emergencyDTOList = emergenceService.getAllEmergencies();

        assertNotNull(emergencyDTOList);
        assertEquals(0, emergencyDTOList.size());
        verify(emergenceRepository, times(1)).findAll();
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

    @Test
    public void getEmergence_whenEmergencyExists_thenShouldReturnEmergencyDTO() {
        String emergencyId = "1";
        EmergencyDTO emergencyDTO = EmergencyDTO.builder()
                .location("testLocation")
                .description("testDescription")
                .numberOfPeople(1).build();

        when(emergenceRepository.findById(emergencyId)).thenReturn(java.util.Optional.of(EmergencyMapper.mapToEmergency(emergencyDTO)));

        EmergencyDTO foundEmergencyDTO = emergenceService.getEmergence(emergencyId);

        assertNotNull(foundEmergencyDTO);
        assertEquals(emergencyDTO, foundEmergencyDTO);
        verify(emergenceRepository, times(1)).findById(emergencyId);
    }

    @Test
    public void getEmergence_whenEmergencyNotExists_thenShouldThrowIllegalArgumentException() {
        String emergencyId = "1";

        when(emergenceRepository.findById(emergencyId)).thenReturn(java.util.Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> emergenceService.getEmergence(emergencyId));
        verify(emergenceRepository, times(1)).findById(emergencyId);
    }

    @Test
    public void updateEmergence_whenEmergencyExists_thenShouldReturnEmergencyDTO() {
        String emergencyId = "1";
        EmergencyDTO emergencyDTO = EmergencyDTO.builder()
                .location("testLocation")
                .description("testDescription")
                .numberOfPeople(1).build();
        Emergency emergency = EmergencyMapper.mapToEmergency(emergencyDTO);

        when(emergenceRepository.findById(emergencyId)).thenReturn(java.util.Optional.of(emergency));
        when(emergenceRepository.save(emergency)).thenReturn(emergency);

        EmergencyDTO updatedEmergencyDTO = emergenceService.updateEmergence(emergencyId, emergencyDTO);

        assertNotNull(updatedEmergencyDTO);
        assertEquals(emergencyDTO, updatedEmergencyDTO);
        verify(emergenceRepository, times(1)).findById(emergencyId);
        verify(emergenceRepository, times(1)).save(emergency);
    }

    @Test
    public void updateEmergence_whenEmergencyNotExists_thenShouldThrowIllegalArgumentException() {
        String emergencyId = "1";
        EmergencyDTO emergencyDTO = EmergencyDTO.builder()
                .location("testLocation")
                .description("testDescription")
                .numberOfPeople(1).build();

        when(emergenceRepository.findById(emergencyId)).thenReturn(java.util.Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> emergenceService.updateEmergence(emergencyId, emergencyDTO));
        verify(emergenceRepository, times(1)).findById(emergencyId);
        verify(emergenceRepository, never()).save(any(Emergency.class));
    }

    @Test
    public void deleteEmergence_whenEmergencyExists_thenShouldReturnTrue() {
        String emergencyId = "1";
        when(emergenceRepository.existsById(emergencyId)).thenReturn(true);

        Boolean isDeleted = emergenceService.deleteEmergence(emergencyId);

        assertNotNull(isDeleted);
        assertTrue(isDeleted);
        verify(emergenceRepository, times(1)).deleteById(emergencyId);
    }

    @Test
    public void deleteEmergence_whenEmergencyNotExists_thenShouldThrowIllegalArgumentException() {
        String emergencyId = "1";
        when(emergenceRepository.existsById(emergencyId)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> emergenceService.deleteEmergence(emergencyId));
        verify(emergenceRepository, never()).deleteById(emergencyId);
    }
}
