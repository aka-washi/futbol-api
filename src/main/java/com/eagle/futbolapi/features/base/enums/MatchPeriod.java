package com.eagle.futbolapi.features.base.enums;

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
}
