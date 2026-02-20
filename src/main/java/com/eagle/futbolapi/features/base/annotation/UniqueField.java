package com.eagle.futbolapi.features.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;

/**
 * Annotation to mark fields that should be considered for uniqueness checks.
 * Supports multi-level hierarchical grouping for complex logical expressions.
 *
 * <h3>Multi-Level Hierarchical Grouping</h3>
 * Fields can be organized into levels, where:
 * <ul>
 *   <li>Fields in the same level are combined using the {@code levelStrategy}</li>
 *   <li>Levels are then combined using the final strategy passed to {@code isDuplicate()}</li>
 * </ul>
 *
 * <h3>Examples</h3>
 *
 * <h4>Example 1: (A AND B) OR C</h4>
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
 * // In service:
 * isDuplicate(entity, UniquenessStrategy.ANY); // Combines levels with OR
 * // Result: (fieldA AND fieldB) OR fieldC
 * </pre>
 *
 * <h4>Example 2: (A OR B) AND (C OR D)</h4>
 * <pre>
 * {@literal @}UniqueField(level = 1, levelStrategy = UniquenessStrategy.ANY)
 * private String fieldA;
 *
 * {@literal @}UniqueField(level = 1, levelStrategy = UniquenessStrategy.ANY)
 * private String fieldB;
 *
 * {@literal @}UniqueField(level = 2, levelStrategy = UniquenessStrategy.ANY)
 * private String fieldC;
 *
 * {@literal @}UniqueField(level = 2, levelStrategy = UniquenessStrategy.ANY)
 * private String fieldD;
 *
 * // In service:
 * isDuplicate(entity, UniquenessStrategy.ALL); // Combines levels with AND
 * // Result: (fieldA OR fieldB) AND (fieldC OR fieldD)
 * </pre>
 *
 * <h4>Example 3: (A AND B) AND C</h4>
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
 * // In service:
 * isDuplicate(entity, UniquenessStrategy.ALL); // Combines levels with AND
 * // Result: (fieldA AND fieldB) AND fieldC = fieldA AND fieldB AND fieldC
 * </pre>
 *
 * <h3>Backward Compatibility</h3>
 * If {@code level} is not specified (defaults to 0), all fields are treated as level 0
 * with the behavior determined by the strategy passed to {@code isDuplicate()}.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueField {

  /**
   * The field path to use for uniqueness checks.
   * If empty, uses the annotated field's name.
   * Supports dot notation for nested fields (e.g., "tournament.id").
   *
   * @return the field path
   */
  String fieldPath() default "";

  /**
   * The level number for multi-level hierarchical grouping.
   * Fields with the same level number are grouped together and combined
   * using the {@link #levelStrategy()}.
   *
   * <p>Default is 0, which maintains backward compatibility with the simple
   * ALL/ANY strategy behavior.
   *
   * @return the level number (must be >= 0)
   */
  int level() default 0;

  /**
   * The strategy to use for combining fields within the same level.
   * <ul>
   *   <li>{@link UniquenessStrategy#ALL} - Fields in this level combined with AND</li>
   *   <li>{@link UniquenessStrategy#ANY} - Fields in this level combined with OR</li>
   * </ul>
   *
   * <p><strong>Important:</strong> All fields in the same level should use the same
   * {@code levelStrategy}. If different strategies are specified for fields in the
   * same level, the last one encountered will be used.
   *
   * <p>Default is {@link UniquenessStrategy#ALL}.
   *
   * @return the strategy for combining fields within this level
   */
  UniquenessStrategy levelStrategy() default UniquenessStrategy.ALL;
}
