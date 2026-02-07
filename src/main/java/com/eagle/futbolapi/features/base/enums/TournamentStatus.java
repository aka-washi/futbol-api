package com.eagle.futbolapi.features.base.enums;

/**
 * Enum defining the status of a tournament.
 */
public enum TournamentStatus {
  ACTIVE("Active"),
  INACTIVE("Inactive"),
  DISCONTINUED("Discontinued"),
  SUSPENDED("Suspended");

  private final String label;

  TournamentStatus(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
