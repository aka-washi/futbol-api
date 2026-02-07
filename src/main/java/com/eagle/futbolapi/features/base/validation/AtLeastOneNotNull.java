package com.eagle.futbolapi.features.base.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Validation annotation to ensure at least one of the specified fields is not null.
 * This is useful for cases where you want to allow either of two fields to be
 * provided but require at least one.
 *
 * <p>
 * Example usage:
 *
 * <pre>
 * {@code @AtLeastOneNotNull(fields = {"organizationId", "organizationDisplayName"},
 *                          message = "Either organizationId or organizationDisplayName must be provided")}
 * public class TournamentDTO { ... }
 * </pre>
 */
@Documented
@Constraint(validatedBy = AtLeastOneNotNullValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AtLeastOneNotNull {

  String message() default "At least one of the specified fields must be provided";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  /**
   * The names of the fields to check. At least one of these fields must be non-null.
   *
   * @return array of field names
   */
  String[] fields();

  /**
   * Support for multiple AtLeastOneNotNull annotations on the same class.
   */
  @Target({ ElementType.TYPE })
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @interface List {
    AtLeastOneNotNull[] value();
  }
}
