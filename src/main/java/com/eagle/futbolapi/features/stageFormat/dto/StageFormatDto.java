package com.eagle.futbolapi.features.stageFormat.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for StageFormat entities.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StageFormatDto {

  private Long id;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;

  @NotBlank(message = "Display name is required")
  @Size(min = 2, max = 100, message = "Display name must be between 2 and 100 characters")
  private String displayName;

  @NotBlank(message = "Type is required")
  private String type;

  private Long pointSystemId;
  private String pointSystemDisplayName;

  private Integer numberOfTeams;
  private Integer numberOfGroups;
  private Integer teamsPerGroup;
  private Boolean hasHomeAndAway;
  private Integer teamsQualifyingForNextStage;
  private String description;
}
