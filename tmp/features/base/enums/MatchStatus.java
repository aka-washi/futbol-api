package com.eagle.futbolapi.features.base.enums;

public enum MatchStatus {
    SCHEDULED("Scheduled"),
    LIVE("Live"),
    HALFTIME("Halftime"),
    FINISHED("Finished"),
    POSTPONED("Postponed"),
    CANCELLED("Cancelled"),
    ABANDONED("Abandoned");

    private final String displayName;

    MatchStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static MatchStatus lookupByDisplayName(final String displayName) {
        for (MatchStatus status : MatchStatus.values()) {
            if (status.getDisplayName().equalsIgnoreCase(displayName)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No match status found with display name: " + displayName);
    }
}
