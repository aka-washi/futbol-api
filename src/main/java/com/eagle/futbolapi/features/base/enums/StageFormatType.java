package com.eagle.futbolapi.features.base.enums;

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
}
