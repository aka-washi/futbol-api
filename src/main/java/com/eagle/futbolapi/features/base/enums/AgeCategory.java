package com.eagle.futbolapi.features.base.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Enum defining age categories for tournaments and teams.
 */
public enum AgeCategory {
  PROFESSIONAL("Professional"),
  RESERVE("Reserve"),
  AMATEUR("Amateur"),
  U23("U-23"),
  U21("U-21"),
  U20("U-20"),
  U19("U-19"),
  U18("U-18"),
  U17("U-17"),
  U16("U-16"),
  U15("U-15"),
  U14("U-14"),
  U13("U-13"),
  U12("U-12"),
  U11("U-11"),
  U10("U-10"),
  U9("U-9"),
  U8("U-8"),
  U7("U-7"),
  U6("U-6"),
  VETERANS("Veterans");

  private final String label;

  AgeCategory(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  @JsonCreator
  public static AgeCategory fromLabel(String label) {
    if (label == null) {
      return null;
    }
    for (AgeCategory e : AgeCategory.values()) {
      if (e.label.equalsIgnoreCase(label) || e.name().equalsIgnoreCase(label)) {
        return e;
      }
    }
    throw new IllegalArgumentException("Unknown AgeCategory: " + label);
  }
}
