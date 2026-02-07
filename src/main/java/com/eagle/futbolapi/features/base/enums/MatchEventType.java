package com.eagle.futbolapi.features.base.enums;

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
}
