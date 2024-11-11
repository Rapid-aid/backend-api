package rapidaid.backend_api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rapidaid.backend_api.models.DTOs.ChangeLocationCrewDTO;
import rapidaid.backend_api.models.DTOs.CrewDTO;
import rapidaid.backend_api.models.DTOs.mappers.CrewMapper;
import rapidaid.backend_api.models.enums.CrewStatus;
import rapidaid.backend_api.models.enums.CrewType;
import rapidaid.backend_api.repositories.CrewRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CrewServiceTest {
    @Mock
    private CrewRepository crewRepository;

    @InjectMocks
    private CrewService crewService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllCrews_whenCrewsExist_thenShouldReturnCrewList() {
        CrewDTO crew1 = CrewDTO.builder()
                .crewCount(1)
                .crewType(CrewType.AMBULANCE)
                .crewStatus(CrewStatus.AVAILABLE)
                .longitude(1.0)
                .latitude(1.0).build();
        CrewDTO crew2 = CrewDTO.builder()
                .crewCount(1)
                .crewType(CrewType.AMBULANCE)
                .crewStatus(CrewStatus.AVAILABLE)
                .longitude(1.0)
                .latitude(1.0).build();
        when(crewRepository.findAll()).thenReturn(List.of(CrewMapper.mapToCrew(crew1), CrewMapper.mapToCrew(crew2)));

        List<CrewDTO> allCrews = crewService.getAllCrews();

        assertEquals(2, allCrews.size());
        assertEquals(crew1, allCrews.get(0));
        assertEquals(crew2, allCrews.get(1));
        verify(crewRepository, times(1)).findAll();
    }

    @Test
    public void getAllCrews_whenCrewsDoNotExist_thenShouldReturnEmptyList() {
        when(crewRepository.findAll()).thenReturn(List.of());

        List<CrewDTO> allCrews = crewService.getAllCrews();

        assertNotNull(allCrews);
        assertEquals(0, allCrews.size());
        verify(crewRepository, times(1)).findAll();
    }

    @Test
    public void getCrewById_whenCrewExists_thenShouldReturnCrew() {
        String crewId = "1";
        CrewDTO crewDTO = CrewDTO.builder()
                .crewCount(1)
                .crewType(CrewType.AMBULANCE)
                .crewStatus(CrewStatus.AVAILABLE)
                .longitude(1.0)
                .latitude(1.0)
                .build();
        when(crewRepository.findById(crewId)).thenReturn(Optional.of(CrewMapper.mapToCrew(crewDTO)));

        CrewDTO foundCrew = crewService.getCrewById(crewId);

        assertNotNull(foundCrew);
        assertEquals(crewDTO, foundCrew);
    }

    @Test
    public void getCrewById_whenCrewDoesNotExist_thenShouldThrowIllegalArgumentException() {
        String crewId = "1";
        when(crewRepository.findById(crewId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> crewService.getCrewById(crewId));
    }

    @Test
    public void createCrew_whenCrewExists_thenShouldReturnCrew() {
        CrewDTO crewDTO = CrewDTO.builder()
                .crewCount(1)
                .crewType(CrewType.AMBULANCE)
                .crewStatus(CrewStatus.AVAILABLE)
                .longitude(1.0)
                .latitude(1.0)
                .build();

        CrewDTO createdCrew = crewService.createCrew(crewDTO);

        assertNotNull(createdCrew);
        assertEquals(crewDTO, createdCrew);
        verify(crewRepository, times(1)).save(any());
    }

    @Test
    public void updateCrew_whenCrewExists_thenShouldReturnUpdatedCrew() {
        String crewId = "1";
        CrewDTO crewDTO = CrewDTO.builder()
                .crewCount(1)
                .crewType(CrewType.AMBULANCE)
                .crewStatus(CrewStatus.AVAILABLE)
                .longitude(1.0)
                .latitude(1.0)
                .build();
        CrewDTO updatedCrewDTO = CrewDTO.builder()
                .crewCount(2)
                .crewType(CrewType.POLICE)
                .crewStatus(CrewStatus.ON_MISSION)
                .longitude(2.0)
                .latitude(2.0)
                .build();
        when(crewRepository.findById(crewId)).thenReturn(Optional.of(CrewMapper.mapToCrew(crewDTO)));

        CrewDTO updatedCrew = crewService.updateCrew(updatedCrewDTO, crewId);

        assertNotNull(updatedCrew);
        assertEquals(updatedCrewDTO, updatedCrew);
        verify(crewRepository, times(1)).save(any());
    }

    @Test
    public void updateCrew_whenCrewDoesNotExist_thenShouldThrowIllegalArgumentException() {
        String crewId = "1";
        CrewDTO crewDTO = CrewDTO.builder()
                .crewCount(1)
                .crewType(CrewType.AMBULANCE)
                .crewStatus(CrewStatus.AVAILABLE)
                .longitude(1.0)
                .latitude(1.0)
                .build();
        when(crewRepository.findById(crewId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> crewService.updateCrew(crewDTO, crewId));
    }

    @Test
    public void changeLocationCrew_whenCrewExists_thenShouldReturnUpdatedCrew() {
        String crewId = "1";
        CrewDTO crewDTO = CrewDTO.builder()
                .crewCount(1)
                .crewType(CrewType.AMBULANCE)
                .crewStatus(CrewStatus.AVAILABLE)
                .longitude(1.0)
                .latitude(1.0)
                .build();
        ChangeLocationCrewDTO changeLocationCrewDTO = ChangeLocationCrewDTO.builder()
                .longitude(2.0)
                .latitude(2.0)
                .build();
        when(crewRepository.findById(crewId)).thenReturn(Optional.of(CrewMapper.mapToCrew(crewDTO)));

        CrewDTO updatedCrew = crewService.changeLocationCrew(changeLocationCrewDTO, crewId);

        assertNotNull(updatedCrew);
        assertEquals(changeLocationCrewDTO.getLongitude(), updatedCrew.getLongitude());
        assertEquals(changeLocationCrewDTO.getLatitude(), updatedCrew.getLatitude());
        assertEquals(crewDTO.getCrewCount(), updatedCrew.getCrewCount());
        assertEquals(crewDTO.getCrewType(), updatedCrew.getCrewType());
        assertEquals(crewDTO.getCrewStatus(), updatedCrew.getCrewStatus());
        verify(crewRepository, times(1)).save(any());
    }

    @Test
    public void changeLocationCrew_whenCrewDoesNotExist_thenShouldThrowIllegalArgumentException() {
        String crewId = "1";
        ChangeLocationCrewDTO changeLocationCrewDTO = ChangeLocationCrewDTO.builder()
                .longitude(2.0)
                .latitude(2.0)
                .build();
        when(crewRepository.findById(crewId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> crewService.changeLocationCrew(changeLocationCrewDTO, crewId));
    }

    @Test
    public void deleteCrew_whenCrewExists_thenShouldReturnTrue() {
        String crewId = "1";
        when(crewRepository.existsById(crewId)).thenReturn(true);

        assertTrue(crewService.deleteCrew(crewId));
        verify(crewRepository).deleteById(crewId);
    }

    @Test
    public void deleteCrew_whenCrewDoesNotExist_thenShouldReturnFalse() {
        String crewId = "1";
        when(crewRepository.existsById(crewId)).thenReturn(false);

        assertFalse(crewService.deleteCrew(crewId));
        verify(crewRepository, never()).deleteById(crewId);
    }
}
