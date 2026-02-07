package com.eagle.futbolapi.features.base.enums;

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
}
