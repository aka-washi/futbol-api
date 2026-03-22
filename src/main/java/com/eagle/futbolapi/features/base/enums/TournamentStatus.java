package com.eagle.futbolapi.features.base.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

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

  @JsonCreator
  public static TournamentStatus fromLabel(String label) {
    if (label == null) {
      return null;
    }
    for (TournamentStatus e : TournamentStatus.values()) {
      if (e.label.equalsIgnoreCase(label) || e.name().equalsIgnoreCase(label)) {
        return e;
      }
    }
    throw new IllegalArgumentException("Unknown TournamentStatus: " + label);
  }
}
