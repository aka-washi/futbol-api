package com.eagle.futbolapi.features.season.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.eagle.futbolapi.features.base.validation.AtLeastOneNotNull;
import com.eagle.futbolapi.features.base.validation.RequiredIfFieldEquals;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AtLeastOneNotNull(fields = { "tournamentId",
    "tournamentDisplayName" }, message = "Either tournamentId or tournamentDisplayName must be provided")
@RequiredIfFieldEquals(field = "endDate", conditionField = "active", conditionValue = "false", message = "End date is required when season is inactive")
public class SeasonDTO {

  private Long id;

  private Long tournamentId;
  private String tournamentDisplayName;

  @NotBlank(message = "Season name is required")
  @Size(min = 2, max = 100, message = "Season name must be between 2 and 100 characters")
  private String name;

  @NotBlank(message = "Display name is required")
  @Size(min = 2, max = 100, message = "Display name must be between 2 and 100 characters")
  private String displayName;

  @NotNull(message = "Start date is required")
  private LocalDate startDate;

  private LocalDate endDate;

  private Boolean active;
  private Boolean hasRelegation;

  @Size(max = 500, message = "Description cannot exceed 500 characters")
  private String description;

  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;
}
