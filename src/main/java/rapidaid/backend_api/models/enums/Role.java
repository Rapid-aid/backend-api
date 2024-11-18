package rapidaid.backend_api.models.enums;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ADMIN"),
    DISPATCHER("DISPATCHER"),
    USER("USER");

    private final String role;

    Role(String role) {
        this.role = role;
    }
}