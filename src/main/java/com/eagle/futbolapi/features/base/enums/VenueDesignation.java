package com.eagle.futbolapi.features.base.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum VenueDesignation {
  PRIMARY("Primary"),
  SECONDARY("Secondary"),
  TEMPORARY("Temporary");

  private final String label;

  VenueDesignation(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  @JsonCreator
  public static VenueDesignation fromLabel(String label) {
    if (label == null) {
      return null;
    }
    for (VenueDesignation e : VenueDesignation.values()) {
      if (e.label.equalsIgnoreCase(label) || e.name().equalsIgnoreCase(label)) {
        return e;
      }
    }
    throw new IllegalArgumentException("Unknown VenueDesignation: " + label);
  }
}
