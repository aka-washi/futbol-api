package com.eagle.futbolapi.features.base.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Enum defining the type of tournament.
 */
public enum TournamentType {
  DOMESTIC_LEAGUE("Domestic League"),
  DOMESTIC_CUP("Domestic Cup"),
  DOMESTIC_SUPER_CUP("Domestic Super Cup"),
  DOMESTIC_PLAYOFF("Domestic Playoff"),
  INTERNATIONAL_LEAGUE("International League"),
  INTERNATIONAL_CUP("International Cup"),
  INTERNATIONAL_SUPER_CUP("International Super Cup"),
  CONTINENTAL_CUP("Continental Cup"),
  CONTINENTAL_LEAGUE("Continental League");

  private final String label;

  TournamentType(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  @JsonCreator
  public static TournamentType fromLabel(String label) {
    if (label == null) {
      return null;
    }
    for (TournamentType e : TournamentType.values()) {
      if (e.label.equalsIgnoreCase(label) || e.name().equalsIgnoreCase(label)) {
        return e;
      }
    }
    throw new IllegalArgumentException("Unknown TournamentType: " + label);
  }
}
