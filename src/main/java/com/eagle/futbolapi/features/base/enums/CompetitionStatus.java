package com.eagle.futbolapi.features.base.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

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

  @JsonCreator
  public static CompetitionStatus fromLabel(String label) {
    if (label == null) {
      return null;
    }
    for (CompetitionStatus e : CompetitionStatus.values()) {
      if (e.label.equalsIgnoreCase(label) || e.name().equalsIgnoreCase(label)) {
        return e;
      }
    }
    throw new IllegalArgumentException("Unknown CompetitionStatus: " + label);
  }
}
