package com.eagle.futbolapi.features.base.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Enum defining the type of competition result.
 */
public enum QualificationType {
  CHAMPION("Champion"),
  LEAGUE_POSITION("League Position"),
  PLAYOFF_WIN("Playoff Win"),
  FAIR_PLAY("Fair Play"),
  OTHER("Other");

  private final String label;

  QualificationType(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  @JsonCreator
  public static QualificationType fromLabel(String label) {
    if (label == null) {
      return null;
    }
    for (QualificationType e : QualificationType.values()) {
      if (e.label.equalsIgnoreCase(label) || e.name().equalsIgnoreCase(label)) {
        return e;
      }
    }
    throw new IllegalArgumentException("Unknown QualificationType: " + label);
  }
}
