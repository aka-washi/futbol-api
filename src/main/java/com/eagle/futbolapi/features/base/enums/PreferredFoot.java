package com.eagle.futbolapi.features.base.enums;

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
}
