package com.eagle.futbolapi.features.base.validation;

import java.lang.reflect.Field;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator implementation for {@link RequiredIfFieldEquals} annotation. Checks
 * that the target
 * field is non-null when the condition field equals the specified value.
 */
public class RequiredIfFieldEqualsValidator
    implements ConstraintValidator<RequiredIfFieldEquals, Object> {

  private String fieldName;
  private String conditionFieldName;
  private String conditionValue;
  private String message;

  @Override
  public void initialize(RequiredIfFieldEquals constraintAnnotation) {
    this.fieldName = constraintAnnotation.field();
    this.conditionFieldName = constraintAnnotation.conditionField();
    this.conditionValue = constraintAnnotation.conditionValue();
    this.message = constraintAnnotation.message();
  }

  @Override
  public boolean isValid(Object object, ConstraintValidatorContext context) {
    if (object == null) {
      return true; // Let @NotNull handle null objects
    }

    try {
      // Get the condition field value
      Field conditionField = getField(object.getClass(), conditionFieldName);
      if (conditionField == null) {
        return true; // Condition field not found, skip validation
      }

      conditionField.setAccessible(true);
      Object conditionFieldValue = conditionField.get(object);

      // Check if the condition is met
      boolean conditionMet = isConditionMet(conditionFieldValue);

      if (!conditionMet) {
        return true; // Condition not met, field is not required
      }

      // Condition is met, check if the target field has a value
      Field targetField = getField(object.getClass(), fieldName);
      if (targetField == null) {
        return true; // Target field not found, skip validation
      }

      targetField.setAccessible(true);
      Object targetValue = targetField.get(object);

      boolean isValid = isValuePresent(targetValue);

      if (!isValid) {
        // Customize the error message and associate with the target field
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addPropertyNode(fieldName)
            .addConstraintViolation();
      }

      return isValid;

    } catch (IllegalAccessException e) {
      return true; // Skip validation on error
    }
  }

  private boolean isConditionMet(Object conditionFieldValue) {
    if (conditionFieldValue == null) {
      return "null".equalsIgnoreCase(conditionValue);
    }

    String actualValue = String.valueOf(conditionFieldValue);
    return conditionValue.equalsIgnoreCase(actualValue);
  }

  private boolean isValuePresent(Object value) {
    if (value == null) {
      return false;
    }
    if (value instanceof String) {
      return !((String) value).trim().isEmpty();
    }
    return true;
  }

  /** Get a field from a class, including inherited fields. */
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
