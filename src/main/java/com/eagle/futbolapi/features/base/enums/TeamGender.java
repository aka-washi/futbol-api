package com.eagle.futbolapi.features.base.enums;

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
}
