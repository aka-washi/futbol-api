package com.eagle.futbolapi.features.base.enums;

/**
 * Enum defining the type of person in a lineup.
 */
public enum PersonType {
  PLAYER("Player"),
  STAFF("Staff");

  private final String label;

  PersonType(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
