package rapidaid.backend_api.models;

import jakarta.persistence.*;

@Entity
@Table(name = "CREWS")
public class Crew {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
}
