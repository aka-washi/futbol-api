package com.eagle.futbolapi.features.base.enums;

/**
 * Enum defining the role of a staff member.
 */
public enum StaffRole {
  HEAD_COACH("Head Coach"),
  ASSISTANT_COACH("Assistant Coach"),
  GOALKEEPER_COACH("Goalkeeper Coach"),
  FITNESS_COACH("Fitness Coach"),
  PHYSIOTHERAPIST("Physiotherapist"),
  DOCTOR("Doctor"),
  MANAGER("Manager"),
  SCOUT("Scout"),
  ANALYST("Analyst");

  private final String label;

  StaffRole(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
