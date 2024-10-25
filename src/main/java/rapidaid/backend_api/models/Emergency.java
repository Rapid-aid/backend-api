package rapidaid.backend_api.models;


import jakarta.persistence.*;

@Entity
@Table(name = "EMERGENCIES")
public class Emergency {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
}
