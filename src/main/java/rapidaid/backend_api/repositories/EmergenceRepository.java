package rapidaid.backend_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rapidaid.backend_api.models.Emergency;

@Repository
public interface EmergenceRepository extends JpaRepository<Emergency, String> {
}
