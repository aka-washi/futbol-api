package com.eagle.futbolapi.features.standing.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandingDTO {

  private Long id;

  @NotNull(message = "Stage is required")
  private Long stageId;
  private String stageDisplayName;

  @NotNull(message = "Team is required")
  private Long teamId;
  private String teamDisplayName;

  private Integer position;
  private Integer played;
  private Integer won;
  private Integer drawn;
  private Integer lost;
  private Integer goalsFor;
  private Integer goalsAgainst;
  private Integer goalDifference;
  private Integer points;
  private String form;
  private String notes;

  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;
}
