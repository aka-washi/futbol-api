package com.eagle.futbolapi.features.structure.entity;

public enum StructureType {
    REGULAR_SEASON("Regular Season"),
    PLAYOFF("Playoff"),
    PLAY_IN("Play In"),
    KNOCKOUT("Knockout"),
    GROUP_STAGE("Group Stage"),
    ROUND_ROBIN("Round Robin");

    private final String displayName;

    StructureType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static StructureType lookupByDisplayName(final String displayName) {
        for (StructureType type : StructureType.values()) {
            if (type.getDisplayName().equalsIgnoreCase(displayName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No structure type found with display name: " + displayName);
    }
}
