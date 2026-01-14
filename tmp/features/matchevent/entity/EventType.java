package com.eagle.futbolapi.features.matchevent.entity;

public enum EventType {
    GOAL("Goal"),
    OWN_GOAL("Own Goal"),
    PENALTY_GOAL("Penalty Goal"),
    PENALTY_MISSED("Penalty Missed"),
    YELLOW_CARD("Yellow Card"),
    RED_CARD("Red Card"),
    SUBSTITUTION("Substitution"),
    VAR_DECISION("VAR Decision"),
    INJURY("Injury");

    private final String displayName;

    EventType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static EventType lookupByDisplayName(final String displayName) {
        for (EventType type : EventType.values()) {
            if (type.getDisplayName().equalsIgnoreCase(displayName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No event type found with display name: " + displayName);
    }
}
