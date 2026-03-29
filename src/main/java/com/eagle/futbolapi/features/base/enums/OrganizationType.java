package com.eagle.futbolapi.features.base.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Enum defining the type of organization.
 */
public enum OrganizationType {
  CLUB("Club"),
  LEAGUE("League"),
  FEDERATION("Federation"),
  CONFEDERATION("Confederation"),
  GOVERNING_BODY("Governing Body");

  private final String label;

  OrganizationType(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  @JsonCreator
  public static OrganizationType fromLabel(String label) {
    if (label == null) {
      return null;
    }
    for (OrganizationType type : OrganizationType.values()) {
      if (type.label.equalsIgnoreCase(label) || type.name().equalsIgnoreCase(label)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Unknown OrganizationType: " + label);
  }
}
