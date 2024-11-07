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
import rapidaid.backend_api.models.enums.PriorityLevel;
import rapidaid.backend_api.models.enums.Status;
import rapidaid.backend_api.models.enums.Type;
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
                .numberOfPeople(1)
                .type(Type.FIRE)
                .status(Status.PENDING)
                .description("testDescription1")
                .priorityLevel(PriorityLevel.HIGH)
                .latitude(1.0)
                .longitude(1.0).build();
        EmergencyDTO emergencyDTO2 = EmergencyDTO.builder()
                .numberOfPeople(2)
                .type(Type.ACCIDENT)
                .status(Status.CANCELLED)
                .description("testDescription2")
                .priorityLevel(PriorityLevel.LOW)
                .latitude(2.0)
                .longitude(2.0).build();

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
                .numberOfPeople(5)
                .type(Type.FIRE)
                .description("testDescription")
                .latitude(1.0)
                .longitude(1.0).build();

        EmergencyDTO emergencyDTO = emergenceService.createEmergence(createEmergencyDTO);

        assertNotNull(emergencyDTO);
        assertEquals(createEmergencyDTO.getLatitude(), emergencyDTO.getLatitude());
        assertEquals(createEmergencyDTO.getLongitude(), emergencyDTO.getLongitude());
        assertEquals(createEmergencyDTO.getDescription(), emergencyDTO.getDescription());
        assertEquals(createEmergencyDTO.getNumberOfPeople(), emergencyDTO.getNumberOfPeople());
        verify(emergenceRepository, times(1)).save(any(Emergency.class));
    }

    @Test
    public void getEmergence_whenEmergencyExists_thenShouldReturnEmergencyDTO() {
        String emergencyId = "1";
        EmergencyDTO emergencyDTO = EmergencyDTO.builder()
                .numberOfPeople(1)
                .type(Type.FIRE)
                .status(Status.PENDING)
                .description("testDescription")
                .priorityLevel(PriorityLevel.HIGH)
                .latitude(1.0)
                .longitude(1.0).build();

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
    public void searchEmergencies_whenEmergenciesHaveSameTypeAndStatus_thenShouldReturnEmergencyDTOList() {
        Type type = Type.FIRE;
        Status status = Status.PENDING;
        String keyword = "testDescription";
        EmergencyDTO emergencyDTO = EmergencyDTO.builder()
                .numberOfPeople(1)
                .type(Type.FIRE)
                .status(Status.PENDING)
                .description("testDescription")
                .priorityLevel(PriorityLevel.HIGH)
                .latitude(1.0)
                .longitude(1.0).build();

        when(emergenceRepository.searchEmergencies(type, status, keyword)).thenReturn(List.of(EmergencyMapper.mapToEmergency(emergencyDTO)));

        List<EmergencyDTO> emergencyDTOList = emergenceService.searchEmergencies(type.toString(), status.toString(), keyword);

        assertNotNull(emergencyDTOList);
        assertEquals(1, emergencyDTOList.size());
        assertEquals(emergencyDTO, emergencyDTOList.getFirst());
        verify(emergenceRepository, times(1)).searchEmergencies(type, status, keyword);
    }

    @Test
    public void searchEmergencies_whenEmergenciesHaveDifferentTypeAndStatus_thenShouldReturnEmptyList() {
        Type type = Type.FIRE;
        Status status = Status.PENDING;

        when(emergenceRepository.searchEmergencies(type, status, null)).thenReturn(List.of());

        List<EmergencyDTO> emergencyDTOList = emergenceService.searchEmergencies(type.toString(), status.toString(), null);

        assertNotNull(emergencyDTOList);
        assertEquals(0, emergencyDTOList.size());
        verify(emergenceRepository, times(1)).searchEmergencies(type, status, null);
    }

    @Test
    public void updateEmergence_whenEmergencyExists_thenShouldReturnEmergencyDTO() {
        String emergencyId = "1";
        EmergencyDTO emergencyDTO = EmergencyDTO.builder()
                .numberOfPeople(1)
                .type(Type.FIRE)
                .status(Status.PENDING)
                .description("testDescription")
                .priorityLevel(PriorityLevel.HIGH)
                .latitude(1.0)
                .longitude(1.0).build();
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
                .numberOfPeople(1)
                .type(Type.FIRE)
                .status(Status.PENDING)
                .description("testDescription")
                .priorityLevel(PriorityLevel.HIGH)
                .latitude(1.0)
                .longitude(1.0).build();

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
