package com.eagle.futbolapi.features.lineup.entity;

public enum LineupType {
    STARTING("Starting"),
    SUBSTITUTE("Substitute");

    private final String displayName;

    LineupType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static LineupType lookupByDisplayName(final String displayName) {
        for (LineupType type : LineupType.values()) {
            if (type.getDisplayName().equalsIgnoreCase(displayName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No lineup type found with display name: " + displayName);
    }
}
