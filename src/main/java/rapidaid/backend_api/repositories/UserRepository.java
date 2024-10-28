package rapidaid.backend_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rapidaid.backend_api.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
