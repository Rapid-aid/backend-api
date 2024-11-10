package rapidaid.backend_api.models.enums;

import lombok.Getter;

@Getter
public enum PriorityLevel {
    LOW ("Low"),
    MEDIUM ("Medium"),
    HIGH ("High"),
    CRITICAL ("Critical");

    private final String priority;

    PriorityLevel(String priority) {
        this.priority = priority;
    }
}