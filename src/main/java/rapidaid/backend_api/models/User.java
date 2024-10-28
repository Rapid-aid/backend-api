package rapidaid.backend_api.models;

import jakarta.persistence.*;
import lombok.*;
import rapidaid.backend_api.models.enums.Role;

@Entity
@Table(name = "USERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private Role role;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String phoneNumber;
}
