package rapidaid.backend_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rapidaid.backend_api.models.Emergency;
import rapidaid.backend_api.models.enums.Status;
import rapidaid.backend_api.models.enums.Type;

import java.util.List;

@Repository
public interface EmergenceRepository extends JpaRepository<Emergency, String> {
    @Query("SELECT e FROM Emergency e " +
            "WHERE (:type IS NULL OR e.type = :type) " +
            "AND (:status IS NULL OR e.status = :status) " +
            "AND (:keyword IS NULL OR e.description LIKE %:keyword%)")
    List<Emergency> searchEmergencies(Type type, Status status, String keyword);
}
