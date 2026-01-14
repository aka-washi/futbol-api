package com.eagle.futbolapi.features.matchday.entity;

public enum MatchdayStatus {
    NOT_STARTED("Not Started"),
    SCHEDULED("Scheduled"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    POSTPONED("Postponed");

    private final String displayName;

    MatchdayStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static MatchdayStatus lookupByDisplayName(final String displayName) {
        for (MatchdayStatus status : MatchdayStatus.values()) {
            if (status.getDisplayName().equalsIgnoreCase(displayName)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No matchday status found with display name: " + displayName);
    }
}
