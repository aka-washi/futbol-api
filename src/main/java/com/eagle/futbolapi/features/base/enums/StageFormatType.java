package com.eagle.futbolapi.features.base.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Enum defining the type of stage format.
 */
public enum StageFormatType {
  REGULAR_SEASON("Regular Season"),
  PLAYOFF("Playoff"),
  PLAY_IN("Play-In"),
  KNOCKOUT("Knockout"),
  GROUP_STAGE("Group Stage"),
  ROUND_ROBIN("Round Robin");

  private final String label;

  StageFormatType(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  @JsonCreator
  public static StageFormatType fromLabel(String label) {
    if (label == null) {
      return null;
    }
    for (StageFormatType e : StageFormatType.values()) {
      if (e.label.equalsIgnoreCase(label) || e.name().equalsIgnoreCase(label)) {
        return e;
      }
    }
    throw new IllegalArgumentException("Unknown StageFormatType: " + label);
  }
}
