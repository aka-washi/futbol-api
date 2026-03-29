package com.eagle.futbolapi.features.base.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MembershipStatus {
  ACTIVE("Active"),
  INACTIVE("Inactive"),
  SUSPENDED("Suspended"),
  WITHDRAWN("Withdrawn"),
  EXPELLED("Expelled");

  private final String label;

  MembershipStatus(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  @JsonCreator
  public static MembershipStatus fromLabel(String label) {
    if (label == null) {
      return null;
    }
    for (MembershipStatus status : MembershipStatus.values()) {
      if (status.label.equalsIgnoreCase(label) || status.name().equalsIgnoreCase(label)) {
        return status;
      }
    }
    throw new IllegalArgumentException("Unknown MembershipStatus: " + label);
  }
}
