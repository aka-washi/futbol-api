package com.eagle.futbolapi.features.team.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Team entities.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto {

  private Long id;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;

  @NotNull(message = "Organization ID is required")
  private Long organizationId;
  private String organizationDisplayName;

  @NotBlank(message = "Team name is required")
  @Size(min = 2, max = 100, message = "Team name must be between 2 and 100 characters")
  private String name;

  @NotBlank(message = "Display name is required")
  @Size(min = 2, max = 100, message = "Display name must be between 2 and 100 characters")
  private String displayName;

  @Size(max = 10, message = "Abbreviation cannot exceed 10 characters")
  private String abbreviation;

  @NotBlank(message = "Gender is required")
  private String gender;

  @NotBlank(message = "Age category is required")
  private String ageCategory;

  @NotNull(message = "Founded date is required")
  private LocalDate founded;

  @NotNull(message = "Country ID is required")
  private Long countryId;

  private String countryDisplayName;

  private Long venueId;

  private String venueDisplayName;

  @Size(max = 500, message = "Logo URL cannot exceed 500 characters")
  private String logo;

  @Size(max = 20, message = "Primary color cannot exceed 20 characters")
  private String primaryColor;

  @Size(max = 20, message = "Secondary color cannot exceed 20 characters")
  private String secondaryColor;

  @Size(max = 100, message = "Website URL cannot exceed 100 characters")
  private String website;

  private String status;

}
