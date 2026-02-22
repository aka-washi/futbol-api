package com.eagle.futbolapi.features.base.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

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

  @JsonCreator
  public static PersonType fromLabel(String label) {
    if (label == null) {
      return null;
    }
    for (PersonType e : PersonType.values()) {
      if (e.label.equalsIgnoreCase(label) || e.name().equalsIgnoreCase(label)) {
        return e;
      }
    }
    throw new IllegalArgumentException("Unknown PersonType: " + label);
  }
}
