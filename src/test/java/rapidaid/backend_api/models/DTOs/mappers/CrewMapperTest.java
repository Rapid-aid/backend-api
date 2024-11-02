package rapidaid.backend_api.models.DTOs.mappers;

import org.junit.jupiter.api.Test;
import rapidaid.backend_api.models.Crew;
import rapidaid.backend_api.models.DTOs.CrewDTO;
import rapidaid.backend_api.models.enums.CrewStatus;
import rapidaid.backend_api.models.enums.CrewType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CrewMapperTest {
    @Test
    public void testMapToCrewDTO() {
        Crew crew = Crew.builder()
                .crewCount(1)
                .crewType(CrewType.AMBULANCE)
                .crewStatus(CrewStatus.AVAILABLE)
                .longitude(1.0)
                .latitude(1.0)
                .build();

        CrewDTO crewDTO = CrewMapper.mapToCrewDTO(crew);

        assertNotNull(crewDTO);
        assertEquals(1, crewDTO.getCrewCount());
        assertEquals(CrewType.AMBULANCE, crewDTO.getCrewType());
        assertEquals(CrewStatus.AVAILABLE, crewDTO.getCrewStatus());
        assertEquals(1.0, crewDTO.getLongitude());
        assertEquals(1.0, crewDTO.getLatitude());
    }

    @Test
    public void testMapToCrew() {
        CrewDTO crewDTO = CrewDTO.builder()
                .crewCount(1)
                .crewType(CrewType.AMBULANCE)
                .crewStatus(CrewStatus.AVAILABLE)
                .longitude(1.0)
                .latitude(1.0)
                .build();

        Crew crew = CrewMapper.mapToCrew(crewDTO);

        assertNotNull(crew);
        assertEquals(1, crew.getCrewCount());
        assertEquals(CrewType.AMBULANCE, crew.getCrewType());
        assertEquals(CrewStatus.AVAILABLE, crew.getCrewStatus());
        assertEquals(1.0, crew.getLongitude());
        assertEquals(1.0, crew.getLatitude());
    }
}
