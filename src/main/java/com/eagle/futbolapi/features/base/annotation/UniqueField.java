package com.eagle.futbolapi.features.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark fields that should be considered for uniqueness checks.
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
}
