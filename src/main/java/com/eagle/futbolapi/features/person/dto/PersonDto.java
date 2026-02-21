package com.eagle.futbolapi.features.person.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.eagle.futbolapi.features.base.validation.AtLeastOneNotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Person entities.
 * Used for transferring person data between the API layer and clients.
 * Contains validation constraints to ensure data integrity.
 *
 * <p>
 * This DTO includes both entity metadata (ID, timestamps, audit fields)
 * and person-specific information (names, birth details, physical attributes).
 *
 * <p>
 * Persons can be players, staff members, referees, or other individuals
 * involved in football activities.
 *
 * @see com.eagle.futbolapi.features.person.entity.Person
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AtLeastOneNotNull.List({
    @AtLeastOneNotNull(fields = { "birthCountryId",
        "birthCountryDisplayName" }, message = "Either birthCountryId or birthCountryDisplayName must be provided"),
    @AtLeastOneNotNull(fields = { "nationalityCountryId",
        "nationalityCountryDisplayName" }, message = "Either nationalityCountryId or nationalityCountryDisplayName must be provided")
})
public class PersonDto {

  /** Unique identifier of the person */
  private Long id;

  /**
   * Unique registration key for the person.
   * Must be unique across all persons in the system.
   */
  @NotBlank(message = "Unique registration key is required")
  private String uniqueRegKey;

  /**
   * First name of the person.
   * Required field.
   */
  @NotBlank(message = "First name is required")
  @Size(min = 1, max = 100, message = "First name must be between 1 and 100 characters")
  private String firstName;

  /**
   * Middle name of the person.
   * Optional field.
   */
  @Size(max = 100, message = "Middle name cannot exceed 100 characters")
  private String middleName;

  /**
   * Last name (surname) of the person.
   * Required field.
   */
  @NotBlank(message = "Last name is required")
  @Size(min = 1, max = 100, message = "Last name must be between 1 and 100 characters")
  private String lastName;

  /**
   * Second last name of the person.
   * Optional field, commonly used in Spanish-speaking countries.
   */
  @Size(max = 100, message = "Second last name cannot exceed 100 characters")
  private String secondLastName;

  /**
   * Display name used for UI presentation.
   * Can be a combination of first name and last name, or a nickname.
   */
  @Size(max = 200, message = "Display name cannot exceed 200 characters")
  private String displayName;

  /**
   * Gender of the person.
   * Examples: MALE, FEMALE
   */
  @NotNull(message = "Gender is required")
  private String gender;

  /**
   * Email address of the person.
   * Must be a valid email format.
   */
  @Email(message = "Email should be valid")
  @Size(max = 100, message = "Email cannot exceed 100 characters")
  private String email;

  /**
   * Date of birth of the person.
   * Required field.
   */
  @NotNull(message = "Birth date is required")
  private LocalDate birthDate;

  /**
   * Place where the person was born.
   * Examples: "London", "Barcelona", "São Paulo"
   */
  @NotBlank(message = "Birth place is required")
  @Size(max = 100, message = "Birth place cannot exceed 100 characters")
  private String birthPlace;

  /** Unique identifier of the country where the person was born */
  private Long birthCountryId;

  /** Display name of the country where the person was born */
  private String birthCountryDisplayName;

  /** Unique identifier of the person's nationality country */
  private Long nationalityCountryId;

  /** Display name of the person's nationality country */
  private String nationalityCountryDisplayName;

  /**
   * Height of the person in meters.
   * Examples: 1.75, 1.82
   */
  private BigDecimal height;

  /**
   * Weight of the person in kilograms.
   * Examples: 70.5, 82.0
   */
  private BigDecimal weight;

  /**
   * URL to the person's photo.
   * Maximum 500 characters.
   */
  @Size(max = 500, message = "Photo URL cannot exceed 500 characters")
  private String photoUrl;

  /** Timestamp when the person was created */
  private LocalDateTime createdAt;

  /** Username of the user who created the person */
  private String createdBy;

  /** Timestamp when the person was last updated */
  private LocalDateTime updatedAt;

  /** Username of the user who last updated the person */
  private String updatedBy;
}
