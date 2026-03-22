package com.eagle.futbolapi.features.base.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Enum defining the status of a stage.
 */
public enum StageStatus {
  NOT_STARTED("Not Started"),
  IN_PROGRESS("In Progress"),
  COMPLETED("Completed");

  private final String label;

  StageStatus(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  @JsonCreator
  public static StageStatus fromLabel(String label) {
    if (label == null) {
      return null;
    }
    for (StageStatus e : StageStatus.values()) {
      if (e.label.equalsIgnoreCase(label) || e.name().equalsIgnoreCase(label)) {
        return e;
      }
    }
    throw new IllegalArgumentException("Unknown StageStatus: " + label);
  }
}
