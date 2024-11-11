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
        assertEquals(crew.getCrewCount(), crewDTO.getCrewCount());
        assertEquals(crew.getCrewType(), crewDTO.getCrewType());
        assertEquals(crew.getCrewStatus(), crewDTO.getCrewStatus());
        assertEquals(crew.getLongitude(), crewDTO.getLongitude());
        assertEquals(crew.getLatitude(), crewDTO.getLatitude());
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
        assertEquals(crewDTO.getCrewCount(), crew.getCrewCount());
        assertEquals(crewDTO.getCrewType(), crew.getCrewType());
        assertEquals(crewDTO.getCrewStatus(), crew.getCrewStatus());
        assertEquals(crewDTO.getLongitude(), crew.getLongitude());
        assertEquals(crewDTO.getLatitude(), crew.getLatitude());
    }
}
