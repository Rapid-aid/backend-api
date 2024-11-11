package rapidaid.backend_api.models.enums;

import lombok.Getter;

@Getter
public enum CrewStatus {
    AVAILABLE("Available"),
    DISPATCHED("Dispatched"),
    ON_MISSION("On Mission"),
    RETURNED("Returned");

    private final String status;

    CrewStatus(String status) {
        this.status = status;
    }
}
