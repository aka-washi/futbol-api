package com.eagle.futbolapi.features.staff.entity;

public enum StaffRole {
    HEAD_COACH("Head Coach"),
    ASSISTANT_COACH("Assistant Coach"),
    GOALKEEPER_COACH("Goalkeeper Coach"),
    FITNESS_COACH("Fitness Coach"),
    MEDICAL_STAFF("Medical Staff"),
    PHYSIOTHERAPIST("Physiotherapist"),
    ANALYST("Analyst"),
    SCOUT("Scout"),
    MANAGER("Manager"),
    OTHER("Other");

    private final String displayName;

    StaffRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static StaffRole lookupByDisplayName(final String displayName) {
        for (StaffRole type : values()) {
            if (type.getDisplayName().equals(displayName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No staff role found with display name: " + displayName);
    }
}
