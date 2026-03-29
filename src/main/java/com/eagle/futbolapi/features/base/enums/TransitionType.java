package com.eagle.futbolapi.features.base.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TransitionType {
  REBRAND("Rebrand"),
  SALE("Sale"),
  REPLACEMENT("Replacement"),
  DISSOLVED("Dissolved"),
  DISAFFILIATED("Disaffiliated");

  private final String label;

  TransitionType(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  @JsonCreator
  public static TransitionType fromLabel(String label) {
    if (label == null) {
      return null;
    }
    for (TransitionType e : TransitionType.values()) {
      if (e.label.equalsIgnoreCase(label) || e.name().equalsIgnoreCase(label)) {
        return e;
      }
    }
    throw new IllegalArgumentException("Unknown OrganizationTransitionType: " + label);
  }
}
