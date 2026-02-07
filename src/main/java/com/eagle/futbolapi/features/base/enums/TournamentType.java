package com.eagle.futbolapi.features.base.enums;

/**
 * Enum defining the type of tournament.
 */
public enum TournamentType {
  LEAGUE("League"),
  CUP("Cup"),
  SUPER_CUP("Super Cup"),
  PLAYOFF("Playoff");

  private final String label;

  TournamentType(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
