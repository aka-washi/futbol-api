
package com.eagle.futbolapi.features.base.entity;

public enum Gender {
  MALE("M", "Male"), FEMALE("F", "Female");

  String value;
  String label;

  Gender(String value, String label) {
    this.value = value;
    this.label = label;
  }

  public String getValue() {
    return value;
  }

  public String getLabel() {
    return label;
  }

  public static Gender lookupByValue(final String value) {
    for (Gender gender : values()) {
      if (gender.getValue().equals(value)) {
        return gender;
      }
    }
    throw new IllegalArgumentException("No gender found for value: " + value);
  }
}
