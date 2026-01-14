package com.eagle.futbolapi.features.competition.entity;

public enum CompetitionType {
    APERTURA("Apertura"),    // Opening tournament (Mexico)
    CLAUSURA("Clausura"),    // Closing tournament (Mexico)
    FULL_SEASON("Full Season"), // Full season (Spain, most European leagues)
    PLAYOFF("Playoff"),     // Playoff stage
    CUP("Cup");

    private final String displayName;

    CompetitionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static CompetitionType lookupByDisplayName(final String displayName) {
        for (CompetitionType type : CompetitionType.values()) {
            if (type.getDisplayName().equalsIgnoreCase(displayName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No competition type found with display name: " + displayName);
    }
}
