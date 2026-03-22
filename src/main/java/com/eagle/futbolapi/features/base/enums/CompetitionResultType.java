package com.eagle.futbolapi.features.base.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Enum defining the type of competition result.
 */
public enum CompetitionResultType {
  CHAMPION("Champion"),
  RUNNER_UP("Runner Up"),
  THIRD_PLACE("Third Place"),
  TOP_SCORER("Top Scorer"),
  BEST_PLAYER("Best Player"),
  BEST_GOALKEEPER("Best Goalkeeper"),
  RELEGATED("Relegated"),
  PROMOTED("Promoted");

  private final String label;

  CompetitionResultType(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  @JsonCreator
  public static CompetitionResultType fromLabel(String label) {
    if (label == null) {
      return null;
    }
    for (CompetitionResultType e : CompetitionResultType.values()) {
      if (e.label.equalsIgnoreCase(label) || e.name().equalsIgnoreCase(label)) {
        return e;
      }
    }
    throw new IllegalArgumentException("Unknown CompetitionResultType: " + label);
  }
}
