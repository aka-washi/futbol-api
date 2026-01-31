package com.eagle.futbolapi.features.base.enums;

/**
 * Enum defining uniqueness checking strategies.
 * - ALL: All unique fields combined must be unique (AND logic)
 * - ANY: At least one unique field must be unique (OR logic)
 */
public enum UniquenessStrategy {
  /**
   * All unique fields combined must be unique.
   * Checks if there's no record with ALL the specified field values.
   */
  ALL,

  /**
   * At least one unique field must be unique.
   * Checks if ANY of the specified fields already exists (duplicate if any match).
   */
  ANY
}
