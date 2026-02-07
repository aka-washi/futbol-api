package com.eagle.futbolapi.features.base.enums;

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
}
