package com.eagle.futbolapi.features.base.enums;

/**
 * Enum defining the status of a tournament season or competition.
 */
public enum CompetitionStatus {
  PLANNED("Planned"),
  IN_PROGRESS("In Progress"),
  COMPLETED("Completed"),
  CANCELLED("Cancelled");

  private final String label;

  CompetitionStatus(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
