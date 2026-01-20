package com.eagle.futbolapi.features.rosterentry.dto;

import java.time.LocalDate;
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
public class RosterEntryDTO {

  private Long id;

  @NotNull(message = "Season is required")
  private Long seasonId;
  private String seasonDisplayName;

  @NotNull(message = "Team is required")
  private Long teamId;
  private String teamDisplayName;

  private Long playerId;
  private String playerDisplayName;

  private Long staffId;
  private String staffDisplayName;

  @NotNull(message = "Entry date is required")
  private LocalDate entryDate;

  private LocalDate exitDate;
  private Integer jerseyNumber;
  private Boolean active;

  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;
}
