package com.eagle.futbolapi.features.base.enums;

/**
 * Enum defining gender for a person.
 */
public enum Gender {
  MALE("Male"),
  FEMALE("Female");

  private final String label;

  Gender(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
