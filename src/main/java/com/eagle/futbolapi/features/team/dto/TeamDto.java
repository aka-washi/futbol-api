package com.eagle.futbolapi.features.team.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.eagle.futbolapi.features.base.validation.AtLeastOneNotNull;

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
@AtLeastOneNotNull.List({
    @AtLeastOneNotNull(fields = { "organizationId",
        "organizationDisplayName" }, message = "Either organizationId or organizationDisplayName must be provided"),
    @AtLeastOneNotNull(fields = { "countryId",
        "countryDisplayName" }, message = "Either countryId or countryDisplayName must be provided")
})
public class TeamDto {

  private Long id;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;

  private Long organizationId;
  private String organizationDisplayName;

  private Long countryId;
  private String countryDisplayName;

  @NotBlank(message = "Team name is required")
  @Size(min = 2, max = 100, message = "Team name must be between 2 and 100 characters")
  private String name;

  @NotBlank(message = "Display name is required")
  @Size(min = 2, max = 100, message = "Display name must be between 2 and 100 characters")
  private String displayName;

  @NotBlank(message = "Gender is required")
  private String gender;

  @NotBlank(message = "Age category is required")
  private String ageCategory;

  @NotNull(message = "Founded date is required")
  private LocalDate founded;

  private Long venueId;
  private String venueDisplayName;

  private String status;

}
