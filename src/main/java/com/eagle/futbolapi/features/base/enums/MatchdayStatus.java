package com.eagle.futbolapi.features.base.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Enum defining the status of a matchday.
 */
public enum MatchdayStatus {
  NOT_STARTED("Not Started"),
  SCHEDULED("Scheduled"),
  IN_PROGRESS("In Progress"),
  COMPLETED("Completed"),
  POSTPONED("Postponed");

  private final String label;

  MatchdayStatus(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  @JsonCreator
  public static MatchdayStatus fromLabel(String label) {
    if (label == null) {
      return null;
    }
    for (MatchdayStatus e : MatchdayStatus.values()) {
      if (e.label.equalsIgnoreCase(label) || e.name().equalsIgnoreCase(label)) {
        return e;
      }
    }
    throw new IllegalArgumentException("Unknown MatchdayStatus: " + label);
  }
}
