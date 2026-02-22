package com.eagle.futbolapi.features.base.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Enum defining the type of match event.
 */
public enum MatchEventType {
  GOAL("Goal"),
  YELLOW_CARD("Yellow Card"),
  RED_CARD("Red Card"),
  SUBSTITUTION("Substitution"),
  PENALTY_SCORED("Penalty Scored"),
  PENALTY_MISSED("Penalty Missed"),
  OWN_GOAL("Own Goal"),
  VAR("VAR");

  private final String label;

  MatchEventType(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  @JsonCreator
  public static MatchEventType fromLabel(String label) {
    if (label == null) {
      return null;
    }
    for (MatchEventType e : MatchEventType.values()) {
      if (e.label.equalsIgnoreCase(label) || e.name().equalsIgnoreCase(label)) {
        return e;
      }
    }
    throw new IllegalArgumentException("Unknown MatchEventType: " + label);
  }
}
