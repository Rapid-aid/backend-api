package rapidaid.backend_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rapidaid.backend_api.models.Crew;
import rapidaid.backend_api.models.DTOs.CrewDTO;
import rapidaid.backend_api.models.DTOs.mappers.CrewMapper;
import rapidaid.backend_api.repositories.CrewRepository;

import java.util.List;

@Service
public class CrewService {
    private final CrewRepository crewRepository;

    @Autowired
    public CrewService(CrewRepository crewRepository) {
        this.crewRepository = crewRepository;
    }

    public List<CrewDTO> getAllCrews() {
        return crewRepository.findAll().stream()
                .map(CrewMapper::mapToCrewDTO)
                .toList();
    }

    public CrewDTO getCrewById(String id) {
        return CrewMapper.mapToCrewDTO(crewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Crew not found")));
    }

    public CrewDTO createCrew(CrewDTO crewDTO) {
        Crew crew = Crew.builder()
                .crewCount(crewDTO.getCrewCount())
                .crewType(crewDTO.getCrewType())
                .crewStatus(crewDTO.getCrewStatus())
                .longitude(crewDTO.getLongitude())
                .latitude(crewDTO.getLatitude())
                .build();

        crewRepository.save(crew);
        return crewDTO;
    }

    public Boolean deleteCrew(String id) {
        if(crewRepository.existsById(id)) {
            crewRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
