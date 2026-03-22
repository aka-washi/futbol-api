package com.eagle.futbolapi.features.base.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Enum defining the period of a match.
 */
public enum MatchPeriod {
  FIRST_HALF("First Half"),
  SECOND_HALF("Second Half"),
  EXTRA_TIME_FIRST_HALF("Extra Time First Half"),
  EXTRA_TIME_SECOND_HALF("Extra Time Second Half"),
  PENALTY_SHOOTOUT("Penalty Shootout");

  private final String label;

  MatchPeriod(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  @JsonCreator
  public static MatchPeriod fromLabel(String label) {
    if (label == null) {
      return null;
    }
    for (MatchPeriod e : MatchPeriod.values()) {
      if (e.label.equalsIgnoreCase(label) || e.name().equalsIgnoreCase(label)) {
        return e;
      }
    }
    throw new IllegalArgumentException("Unknown MatchPeriod: " + label);
  }
}
