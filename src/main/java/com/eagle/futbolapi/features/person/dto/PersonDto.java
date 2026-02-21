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
  private Long id;
  @NotBlank(message = "Unique registration key is required")
  private String uniqueRegKey;
  @NotBlank(message = "First name is required")
  @Size(min = 1, max = 100, message = "First name must be between 1 and 100 characters")
  private String firstName;
  @Size(max = 100, message = "Middle name cannot exceed 100 characters")
  private String middleName;
  @NotBlank(message = "Last name is required")
  @Size(min = 1, max = 100, message = "Last name must be between 1 and 100 characters")
  private String lastName;
  @Size(max = 100, message = "Second last name cannot exceed 100 characters")
  private String secondLastName;
  @Size(max = 200, message = "Display name cannot exceed 200 characters")
  private String displayName;
  @NotNull(message = "Gender is required")
  private String gender;
  @Email(message = "Email should be valid")
  @Size(max = 100, message = "Email cannot exceed 100 characters")
  private String email;
  @NotNull(message = "Birth date is required")
  private LocalDate birthDate;
  @NotBlank(message = "Birth place is required")
  @Size(max = 100, message = "Birth place cannot exceed 100 characters")
  private String birthPlace;
  private Long birthCountryId;
  private String birthCountryDisplayName;
  private Long nationalityCountryId;
  private String nationalityCountryDisplayName;

  /**
 * Height of the person in meters.
 */
  private BigDecimal height;

  /**
 * Weight of the person in kilograms.
 */
  private BigDecimal weight;
  @Size(max = 500, message = "Photo URL cannot exceed 500 characters")
  private String photoUrl;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;
}
