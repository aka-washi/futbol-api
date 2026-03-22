package com.eagle.futbolapi.features.base.enums;

public enum AgeCategory {
  PROFESSIONAL("Professional"),
  RESERVE("Reserve"),
  AMATEUR("Amateur"),
  U23("Under 23"),
  U21("Under 21"),
  U20("Under 20"),
  U19("Under 19"),
  U18("Under 18"),
  U17("Under 17"),
  U16("Under 16"),
  U15("Under 15"),
  U14("Under 14"),
  U13("Under 13"),
  U12("Under 12"),
  U11("Under 11"),
  U10("Under 10"),
  U9("Under 9"),
  U8("Under 8"),
  U7("Under 7"),
  U6("Under 6"),
  VETERANS("Veterans");

  private final String displayName;

  AgeCategory(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public static AgeCategory lookupByDisplayName(final String displayName) {
    for (AgeCategory type : values()) {
      if (type.getDisplayName().equals(displayName)) {
        return type;
      }
    }
    return null;
  }

}
