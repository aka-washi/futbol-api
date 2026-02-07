package com.eagle.futbolapi.features.base.validation;

import java.lang.reflect.Field;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator implementation for {@link AtLeastOneNotNull} annotation.
 * Checks that at least one of the specified fields has a non-null value.
 * For String fields, also checks that the value is not empty or blank.
 */
public class AtLeastOneNotNullValidator implements ConstraintValidator<AtLeastOneNotNull, Object> {

  private String[] fields;
  private String message;

  @Override
  public void initialize(AtLeastOneNotNull constraintAnnotation) {
    this.fields = constraintAnnotation.fields();
    this.message = constraintAnnotation.message();
  }

  @Override
  public boolean isValid(Object object, ConstraintValidatorContext context) {
    if (object == null) {
      return true; // Let @NotNull handle null objects
    }

    boolean atLeastOneProvided = false;

    for (String fieldName : fields) {
      try {
        Field field = getField(object.getClass(), fieldName);
        if (field != null) {
          field.setAccessible(true);
          Object value = field.get(object);

          if (value != null) {
            // For strings, also check if not empty/blank
            if (value instanceof String) {
              if (!((String) value).trim().isEmpty()) {
                atLeastOneProvided = true;
                break;
              }
            } else {
              atLeastOneProvided = true;
              break;
            }
          }
        }
      } catch (IllegalAccessException e) {
        // Field not accessible, skip
      }
    }

    if (!atLeastOneProvided) {
      // Customize the error message
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
      return false;
    }

    return true;
  }

  /**
   * Get a field from a class, including inherited fields.
   */
  private Field getField(Class<?> clazz, String fieldName) {
    Class<?> currentClass = clazz;
    while (currentClass != null && currentClass != Object.class) {
      try {
        return currentClass.getDeclaredField(fieldName);
      } catch (NoSuchFieldException e) {
        currentClass = currentClass.getSuperclass();
      }
    }
    return null;
  }
}
