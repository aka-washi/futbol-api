package com.eagle.futbolapi.features.base.enums;

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
}
