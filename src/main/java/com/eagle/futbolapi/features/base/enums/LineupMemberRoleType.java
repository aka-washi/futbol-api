package com.eagle.futbolapi.features.base.enums;

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
}
