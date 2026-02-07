
package com.eagle.futbolapi.features.base.enums;

public enum Position {
  GOALKEEPER("GK", "Goalkeeper"),
  DEFENDER("DF", "Defender"),
  MIDFIELDER("MF", "Midfielder"),
  FORWARD("FW", "Forward");

  private final String code;
  private final String label;

  Position(String code, String label) {
    this.code = code;
    this.label = label;
  }

  public String getCode() {
    return code;
  }

  public String getLabel() {
    return label;
  }

  public static Position lookupByCode(final String code) {
    if (code == null || code.isEmpty()) {
      return null;
    }
    for (Position position : values()) {
      if (position.getCode().equals(code)) {
        return position;
      }
    }
    throw new IllegalArgumentException("No position found for code: " + code);
  }
}
