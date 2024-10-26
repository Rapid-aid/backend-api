package rapidaid.backend_api.models.enums;

public enum Role {
    ADMIN("ADMIN"),
    DISPATCHER("DISPATCHER"),
    RESPONDER("RESPONDER");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}