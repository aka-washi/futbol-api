package com.eagle.futbolapi.features.base.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Uniqueness checking strategies for combining fields.
 */
public enum UniquenessStrategy {
  ALL,
  ANY;

  @JsonCreator
  public static UniquenessStrategy fromLabel(String label) {
    if (label == null) {
      return null;
    }
    for (UniquenessStrategy e : UniquenessStrategy.values()) {
      if (e.name().equalsIgnoreCase(label)) {
        return e;
      }
    }
    throw new IllegalArgumentException("Unknown UniquenessStrategy: " + label);
  }
}
