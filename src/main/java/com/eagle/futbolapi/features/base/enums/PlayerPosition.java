package com.eagle.futbolapi.features.base.enums;

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
}
