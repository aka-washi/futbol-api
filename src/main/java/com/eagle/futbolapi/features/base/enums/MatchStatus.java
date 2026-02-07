package com.eagle.futbolapi.features.base.enums;

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
}
