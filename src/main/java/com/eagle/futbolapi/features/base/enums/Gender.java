package com.eagle.futbolapi.features.base.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

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

  @JsonCreator
  public static Gender fromLabel(String label) {
    if (label == null) {
      return null;
    }
    for (Gender e : Gender.values()) {
      if (e.label.equalsIgnoreCase(label) || e.name().equalsIgnoreCase(label)) {
        return e;
      }
    }
    throw new IllegalArgumentException("Unknown Gender: " + label);
  }
}
