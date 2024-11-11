package rapidaid.backend_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rapidaid.backend_api.models.Crew;
import rapidaid.backend_api.models.DTOs.ChangeLocationCrewDTO;
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
        Crew crew = CrewMapper.mapToCrew(crewDTO);

        crewRepository.save(crew);
        return crewDTO;
    }

    public CrewDTO updateCrew(CrewDTO crewDTO, String id) {
        Crew crew = crewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Crew not found"));
        if(!crewDTO.getCrewCount().equals(crew.getCrewCount())) {
            crew.setCrewCount(crewDTO.getCrewCount());
        }
        if(!crewDTO.getCrewType().equals(crew.getCrewType())) {
            crew.setCrewType(crewDTO.getCrewType());
        }
        if(!crewDTO.getCrewStatus().equals(crew.getCrewStatus())) {
            crew.setCrewStatus(crewDTO.getCrewStatus());
        }
        if(crewDTO.getLongitude() != crew.getLongitude()) {
            crew.setLongitude(crewDTO.getLongitude());
        }
        if(crewDTO.getLatitude() != crew.getLatitude()) {
            crew.setLatitude(crewDTO.getLatitude());
        }
        crewRepository.save(crew);
        return CrewMapper.mapToCrewDTO(crew);
    }

    public CrewDTO changeLocationCrew(ChangeLocationCrewDTO changeLocationCrewDTO, String id) {
        Crew crew = crewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Crew not found"));
        crew.setLongitude(changeLocationCrewDTO.getLongitude());
        crew.setLatitude(changeLocationCrewDTO.getLatitude());
        crewRepository.save(crew);
        return CrewMapper.mapToCrewDTO(crew);
    }

    public Boolean deleteCrew(String id) {
        if(crewRepository.existsById(id)) {
            crewRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
