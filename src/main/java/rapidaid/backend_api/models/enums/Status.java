package rapidaid.backend_api.models.enums;

import lombok.Getter;

@Getter
public enum Status {
    PENDING("Pending"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private final String status;

    Status(String status) {
        this.status = status;
    }
}