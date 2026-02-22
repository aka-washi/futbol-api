package com.eagle.futbolapi.features.base.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Enum defining the preferred foot of a player.
 */
public enum PreferredFoot {
  LEFT("Left"),
  RIGHT("Right"),
  BOTH("Both");

  private final String label;

  PreferredFoot(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  @JsonCreator
  public static PreferredFoot fromLabel(String label) {
    if (label == null) {
      return null;
    }
    for (PreferredFoot e : PreferredFoot.values()) {
      if (e.label.equalsIgnoreCase(label) || e.name().equalsIgnoreCase(label)) {
        return e;
      }
    }
    throw new IllegalArgumentException("Unknown PreferredFoot: " + label);
  }
}
