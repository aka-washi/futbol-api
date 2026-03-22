package com.eagle.futbolapi.features.base.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

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

  @JsonCreator
  public static StaffRole fromLabel(String label) {
    if (label == null) {
      return null;
    }
    for (StaffRole e : StaffRole.values()) {
      if (e.label.equalsIgnoreCase(label) || e.name().equalsIgnoreCase(label)) {
        return e;
      }
    }
    throw new IllegalArgumentException("Unknown StaffRole: " + label);
  }
}
