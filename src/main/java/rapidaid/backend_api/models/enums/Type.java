package rapidaid.backend_api.models.enums;

import lombok.Getter;

@Getter
public enum Type {
    FIRE("Fire"),
    FLOOD("Flood"),
    EARTHQUAKE("Earthquake"),
    ACCIDENT("Accident"),
    ANOTHER("Another");

    private final String type;

    Type(String type) {
        this.type = type;
    }
}
