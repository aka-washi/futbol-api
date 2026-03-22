package com.eagle.futbolapi.features.base.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Enum defining the type of competition.
 */
public enum CompetitionType {
  APERTURA("Apertura"),
  CLAUSURA("Clausura"),
  FULL_SEASON("Full Season"),
  PLAYOFF("Playoff"),
  CUP("Cup");

  private final String label;

  CompetitionType(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  @JsonCreator
  public static CompetitionType fromLabel(String label) {
    if (label == null) {
      return null;
    }
    for (CompetitionType e : CompetitionType.values()) {
      if (e.label.equalsIgnoreCase(label) || e.name().equalsIgnoreCase(label)) {
        return e;
      }
    }
    throw new IllegalArgumentException("Unknown CompetitionType: " + label);
  }
}
