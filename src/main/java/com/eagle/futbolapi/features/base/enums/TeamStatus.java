package com.eagle.futbolapi.features.base.enums;

/**
 * Enum defining the status of a team.
 */
public enum TeamStatus {
  ACTIVE("Active"),
  INACTIVE("Inactive"),
  DORMANT("Dormant"),
  DEFUNCT("Defunct");

  private final String label;

  TeamStatus(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
