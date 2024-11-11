package rapidaid.backend_api.models.DTOs.mappers;

import rapidaid.backend_api.models.Crew;
import rapidaid.backend_api.models.DTOs.CrewDTO;

public class CrewMapper {
    public static CrewDTO mapToCrewDTO(Crew crew) {
        return CrewDTO.builder()
                .crewCount(crew.getCrewCount())
                .crewType(crew.getCrewType())
                .crewStatus(crew.getCrewStatus())
                .longitude(crew.getLongitude())
                .latitude(crew.getLatitude())
                .build();
    }

    public static Crew mapToCrew(CrewDTO crewDTO) {
        return Crew.builder()
                .crewCount(crewDTO.getCrewCount())
                .crewType(crewDTO.getCrewType())
                .crewStatus(crewDTO.getCrewStatus())
                .longitude(crewDTO.getLongitude())
                .latitude(crewDTO.getLatitude())
                .build();
    }
}
