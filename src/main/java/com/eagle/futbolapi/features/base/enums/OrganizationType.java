package com.eagle.futbolapi.features.base.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Enum defining the type of organization.
 */
public enum OrganizationType {
  CLUB("Club"),
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
    for (OrganizationType e : OrganizationType.values()) {
      if (e.label.equalsIgnoreCase(label) || e.name().equalsIgnoreCase(label)) {
        return e;
      }
    }
    throw new IllegalArgumentException("Unknown OrganizationType: " + label);
  }
}
