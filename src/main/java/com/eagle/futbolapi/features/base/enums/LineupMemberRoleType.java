package com.eagle.futbolapi.features.base.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Enum defining the role type of a lineup member.
 */
public enum LineupMemberRoleType {
  STARTING("Starting"),
  SUBSTITUTE("Substitute"),
  COACH("Coach"),
  ASSISTANT_COACH("Assistant Coach"),
  MEDICAL_STAFF("Medical Staff");

  private final String label;

  LineupMemberRoleType(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  @JsonCreator
  public static LineupMemberRoleType fromLabel(String label) {
    if (label == null) {
      return null;
    }
    for (LineupMemberRoleType e : LineupMemberRoleType.values()) {
      if (e.label.equalsIgnoreCase(label) || e.name().equalsIgnoreCase(label)) {
        return e;
      }
    }
    throw new IllegalArgumentException("Unknown LineupMemberRoleType: " + label);
  }
}
