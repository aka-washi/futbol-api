package com.eagle.futbolapi.features.base.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Enum defining the position of a player.
 */
public enum PlayerPosition {
  GOALKEEPER("Goalkeeper"),
  DEFENDER("Defender"),
  MIDFIELDER("Midfielder"),
  FORWARD("Forward");

  private final String label;

  PlayerPosition(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  @JsonCreator
  public static PlayerPosition fromLabel(String label) {
    if (label == null) {
      return null;
    }
    for (PlayerPosition e : PlayerPosition.values()) {
      if (e.label.equalsIgnoreCase(label) || e.name().equalsIgnoreCase(label)) {
        return e;
      }
    }
    throw new IllegalArgumentException("Unknown PlayerPosition: " + label);
  }
}
