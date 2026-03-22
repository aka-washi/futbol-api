package com.eagle.futbolapi.features.base.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Enum defining the status of a match.
 */
public enum MatchStatus {
  SCHEDULED("Scheduled"),
  IN_PROGRESS("In Progress"),
  COMPLETED("Completed"),
  POSTPONED("Postponed"),
  CANCELLED("Cancelled");

  private final String label;

  MatchStatus(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  @JsonCreator
  public static MatchStatus fromLabel(String label) {
    if (label == null) {
      return null;
    }
    for (MatchStatus e : MatchStatus.values()) {
      if (e.label.equalsIgnoreCase(label) || e.name().equalsIgnoreCase(label)) {
        return e;
      }
    }
    throw new IllegalArgumentException("Unknown MatchStatus: " + label);
  }
}
