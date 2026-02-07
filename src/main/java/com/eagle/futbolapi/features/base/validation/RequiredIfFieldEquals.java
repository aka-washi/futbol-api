package com.eagle.futbolapi.features.base.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Validation annotation to conditionally require a field based on another field's value.
 * The target field is required (must be non-null) when the condition field
 * equals the specified value.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * {@code @RequiredIfFieldEquals(
 *     field = "endDate",
 *     conditionField = "active",
 *     conditionValue = "false",
 *     message = "End date is required when season is inactive"
 * )}
 * public class SeasonDTO { ... }
 * </pre>
 */
@Documented
@Constraint(validatedBy = RequiredIfFieldEqualsValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(RequiredIfFieldEquals.List.class)
public @interface RequiredIfFieldEquals {

  String message() default "Field is required when condition is met";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  /**
   * The name of the field that should be validated (required when condition is
   * met).
   */
  String field();

  /** The name of the field to check for the condition. */
  String conditionField();

  /**
   * The value that the condition field must equal for the validation to apply.
   * The value is
   * compared as a string representation.
   */
  String conditionValue();

  /** Support for multiple RequiredIfFieldEquals annotations on the same class. */
  @Target({ ElementType.TYPE })
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @interface List {
    RequiredIfFieldEquals[] value();
  }
}
