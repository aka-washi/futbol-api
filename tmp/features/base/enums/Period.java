package com.eagle.futbolapi.features.base.enums;

public enum Period {
  FIRST_HALF("First Half"),
  SECOND_HALF("Second Half"),
  EXTRA_TIME_FIRST("Extra Time First"),
  EXTRA_TIME_SECOND("Extra Time Second"),
  PENALTY_SHOOTOUT("Penalty Shootout");

  private final String displayName;

  Period(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public static Period lookupByDisplayName(final String displayName) {
    for (Period period : Period.values()) {
      if (period.getDisplayName().equalsIgnoreCase(displayName)) {
        return period;
      }
    }
    throw new IllegalArgumentException("No period found with display name: " + displayName);
  }
}
