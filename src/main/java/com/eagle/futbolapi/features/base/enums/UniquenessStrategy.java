package com.eagle.futbolapi.features.base.enums;

/**
 * Enum defining uniqueness checking strategies for combining fields.
 *
 * <ul>
 *   <li><strong>ALL:</strong> AND logic - all fields must match for duplicate</li>
 *   <li><strong>ANY:</strong> OR logic - any field matching is a duplicate</li>
 * </ul>
 */
public enum UniquenessStrategy {
  /**
   * ALL strategy uses AND logic.
   * A duplicate is found only when ALL specified fields have matching values.
   */
  ALL,

  /**
   * ANY strategy uses OR logic.
   * A duplicate is found when ANY of the specified fields have matching values.
   */
  ANY
}
