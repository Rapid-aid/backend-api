package rapidaid.backend_api.models.enums;

import lombok.Getter;

@Getter
public enum CrewType {
    AMBULANCE("Ambulance"),
    FIRE_DEPARTMENT("Fire Department"),
    POLICE("Police");

    private final String type;

    CrewType(String type) {
        this.type = type;
    }
}
