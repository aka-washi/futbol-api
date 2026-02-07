package com.eagle.futbolapi.features.base.enums;

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
}
