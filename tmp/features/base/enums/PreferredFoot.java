
package com.eagle.futbolapi.features.base.enums;

public enum PreferredFoot {
  LEFT("L", "Left"),
  RIGHT("R", "Right"),
  BOTH("B", "Both");

  private final String code;
  private final String label;

  PreferredFoot(String code, String label) {
    this.code = code;
    this.label = label;
  }

  public String getCode() {
    return code;
  }

  public String getLabel() {
    return label;
  }

  public static PreferredFoot lookupByCode(final String code) {
    if (code == null || code.isEmpty()) {
      return null;
    }
    for (PreferredFoot foot : values()) {
      if (foot.getCode().equals(code)) {
        return foot;
      }
    }
    throw new IllegalArgumentException("No preferred foot found for code: " + code);
  }
}
