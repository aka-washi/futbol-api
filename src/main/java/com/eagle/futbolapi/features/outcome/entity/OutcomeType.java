package com.eagle.futbolapi.features.outcome.entity;

public enum OutcomeType {
    CHAMPION("Champion"),
    RUNNER_UP("Runner-up"),
    THIRD_PLACE("Third Place"),
    FOURTH_PLACE("Fourth Place"),
    QUALIFIED_INTERNATIONAL("Qualified for International Competitions"), // Qualified for international competitions
    RELEGATED("Relegated"),
    PROMOTED("Promoted"),
    TOP_SCORER("Top Scorer"),
    BEST_PLAYER("Best Player"),
    BEST_GOALKEEPER("Best Goalkeeper"),
    BEST_COACH("Best Coach"),
    FAIR_PLAY_AWARD("Fair Play Award");

    private final String displayName;

    OutcomeType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static OutcomeType lookupByDisplayName(final String displayName) {
        for (OutcomeType type : OutcomeType.values()) {
            if (type.getDisplayName().equalsIgnoreCase(displayName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No outcome type found with display name: " + displayName);
    }
}
