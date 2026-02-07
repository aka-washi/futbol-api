package com.eagle.futbolapi.features.base.enums;

public enum TournamentType {
  LEAGUE("League"),
  CUP("Cup"),
  SUPER_CUP("Super Cup"),
  PLAYOFF("Playoff");

  private final String displayName;

  TournamentType(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public static TournamentType lookupByDisplayName(final String displayName) {
    for (TournamentType type : values()) {
      if (type.getDisplayName().equals(displayName)) {
        return type;
      }
    }
    throw new IllegalArgumentException("No tournament type found with display name: " + displayName);
  }
}
