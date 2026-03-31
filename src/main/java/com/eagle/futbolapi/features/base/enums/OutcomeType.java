package com.eagle.futbolapi.features.base.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OutcomeType {
  CHAMPION("Champion"),
  RUNNER_UP("Runner Up"),
  THIRD_PLACE("Third Place"),
  PROMOTED("Promoted"),
  RELEGATED("Relegated"),
  QUALIFIED("Qualified"),
  ELIMINATED("Eliminated"),
  NONE("None");

  private final String label;

  OutcomeType(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  @JsonCreator
  public static OutcomeType fromLabel(String label) {
    if (label == null) {
      return null;
    }
    for (OutcomeType e : OutcomeType.values()) {
      if (e.label.equalsIgnoreCase(label) || e.name().equalsIgnoreCase(label)) {
        return e;
      }
    }
    throw new IllegalArgumentException("Unknown OutcomeType: " + label);
  }
}
