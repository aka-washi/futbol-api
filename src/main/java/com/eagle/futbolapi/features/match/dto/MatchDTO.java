package com.eagle.futbolapi.features.match.dto;

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
public class MatchDTO {

  private Long id;

  @NotNull(message = "Matchday is required")
  private Long matchdayId;
  private String matchdayDisplayName;

  @NotNull(message = "Home team is required")
  private Long homeTeamId;
  private String homeTeamDisplayName;

  @NotNull(message = "Away team is required")
  private Long awayTeamId;
  private String awayTeamDisplayName;

  private Long venueId;
  private String venueName;

  private Long refereeId;
  private String refereeName;

  @NotNull(message = "Match date is required")
  private LocalDate matchDate;

  private LocalDateTime kickoffTime;

  private Integer homeTeamScore;
  private Integer awayTeamScore;

  private String status;
  private Integer duration;
  private String notes;

  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;
}
