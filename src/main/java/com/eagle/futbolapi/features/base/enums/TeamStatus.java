package com.eagle.futbolapi.features.base.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

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

  @JsonCreator
  public static TeamStatus fromLabel(String label) {
    if (label == null) {
      return null;
    }
    for (TeamStatus e : TeamStatus.values()) {
      if (e.label.equalsIgnoreCase(label) || e.name().equalsIgnoreCase(label)) {
        return e;
      }
    }
    throw new IllegalArgumentException("Unknown TeamStatus: " + label);
  }
}
