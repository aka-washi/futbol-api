package com.eagle.futbolapi.features.base.enums;

/**
 * Enum defining uniqueness checking strategies for combining fields or levels.
 *
 * <h3>Usage Contexts</h3>
 * This strategy is used in two contexts:
 * <ol>
 *   <li><strong>Level Strategy:</strong> Combines fields within a single level
 *       (specified in {@code @UniqueField(levelStrategy = ...)})</li>
 *   <li><strong>Final Strategy:</strong> Combines multiple levels together
 *       (specified in {@code isDuplicate(entity, strategy)})</li>
 * </ol>
 *
 * <h3>Strategies</h3>
 * <ul>
 *   <li><strong>ALL:</strong> AND logic - all elements must match for duplicate</li>
 *   <li><strong>ANY:</strong> OR logic - any element matching is a duplicate</li>
 * </ul>
 *
 * <h3>Examples</h3>
 *
 * <h4>As Level Strategy (within a level):</h4>
 * <pre>
 * // Level 1 with ALL strategy
 * {@literal @}UniqueField(level = 1, levelStrategy = UniquenessStrategy.ALL)
 * private String name;
 *
 * {@literal @}UniqueField(level = 1, levelStrategy = UniquenessStrategy.ALL)
 * private String code;
 *
 * // Within Level 1: name AND code (both must match)
 * </pre>
 *
 * <h4>As Final Strategy (between levels):</h4>
 * <pre>
 * // Two levels with final strategy ANY
 * isDuplicate(entity, UniquenessStrategy.ANY);
 * // Result: Level1 OR Level2 (either level matching is duplicate)
 *
 * // Two levels with final strategy ALL
 * isDuplicate(entity, UniquenessStrategy.ALL);
 * // Result: Level1 AND Level2 (both levels must match)
 * </pre>
 *
 * <h4>Complete Example: (A AND B) OR C</h4>
 * <pre>
 * {@literal @}UniqueField(level = 1, levelStrategy = UniquenessStrategy.ALL)
 * private String fieldA;
 *
 * {@literal @}UniqueField(level = 1, levelStrategy = UniquenessStrategy.ALL)
 * private String fieldB;
 *
 * {@literal @}UniqueField(level = 2, levelStrategy = UniquenessStrategy.ANY)
 * private String fieldC;
 *
 * // Level 1 strategy ALL → (fieldA AND fieldB)
 * // Level 2 strategy ANY → fieldC
 * // Final strategy ANY → (fieldA AND fieldB) OR fieldC
 * isDuplicate(entity, UniquenessStrategy.ANY);
 * </pre>
 */
public enum UniquenessStrategy {
  /**
   * ALL strategy uses AND logic.
   *
   * <p><strong>As Level Strategy:</strong> All fields in the level must match
   * (fieldA AND fieldB)</p>
   *
   * <p><strong>As Final Strategy:</strong> All levels must match
   * (Level1 AND Level2)</p>
   *
   * <p>Example: If checking uniqueness with ALL, a duplicate is found only when
   * ALL specified fields/levels have matching values.</p>
   */
  ALL,

  /**
   * ANY strategy uses OR logic.
   *
   * <p><strong>As Level Strategy:</strong> Any field in the level matching is a duplicate
   * (fieldA OR fieldB)</p>
   *
   * <p><strong>As Final Strategy:</strong> Any level matching is a duplicate
   * (Level1 OR Level2)</p>
   *
   * <p>Example: If checking uniqueness with ANY, a duplicate is found when
   * ANY of the specified fields/levels have matching values.</p>
   */
  ANY
}
