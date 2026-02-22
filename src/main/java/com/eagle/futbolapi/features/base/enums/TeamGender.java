package com.eagle.futbolapi.features.base.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Enum defining gender category for tournaments and teams.
 */
public enum TeamGender {
  MEN("Men"),
  WOMEN("Women");

  private final String label;

  TeamGender(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  @JsonCreator
  public static TeamGender fromLabel(String label) {
    if (label == null) {
      return null;
    }
    for (TeamGender e : TeamGender.values()) {
      if (e.label.equalsIgnoreCase(label) || e.name().equalsIgnoreCase(label)) {
        return e;
      }
    }
    throw new IllegalArgumentException("Unknown TeamGender: " + label);
  }
}
