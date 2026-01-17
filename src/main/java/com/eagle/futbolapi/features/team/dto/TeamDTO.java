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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {

  private Long id;

  @NotBlank(message = "Team name is required")
  @Size(min = 2, max = 100, message = "Team name must be between 2 and 100 characters")
  private String name;

  @NotBlank(message = "Display name is required")
  @Size(min = 2, max = 100, message = "Display name must be between 2 and 100 characters")
  private String displayName;

  @NotBlank(message = "Team code is required")
  @Size(min = 3, max = 3, message = "Team code must be exactly 3 characters")
  private String code;

  @NotNull(message = "Gender is required")
  private String gender;

  @NotNull(message = "Age category is required")
  private String ageCategory;

  @NotNull(message = "Founded date is required")
  private LocalDate founded;

  private Long organizationId;
  private String organizationDisplayName;

  private Long countryId;
  private String countryDisplayName;

  private Long venueId;
  private String venueDisplayName;

  private String logo;

  @Size(max = 7, message = "Primary color cannot exceed 7 characters")
  private String primaryColor;

  @Size(max = 7, message = "Secondary color cannot exceed 7 characters")
  private String secondaryColor;

  private String website;

  private Boolean active;

  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;
}
