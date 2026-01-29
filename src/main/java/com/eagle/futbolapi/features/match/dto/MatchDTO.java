package com.eagle.futbolapi.features.match.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.base.validation.AtLeastOneNotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AtLeastOneNotNull.List({
    @AtLeastOneNotNull(fields = { "matchdayId",
        "matchdayDisplayName" }, message = "Either matchdayId or matchdayDisplayName must be provided"),
    @AtLeastOneNotNull(fields = { "homeTeamId",
        "homeTeamDisplayName" }, message = "Either homeTeamId or homeTeamDisplayName must be provided"),
    @AtLeastOneNotNull(fields = { "awayTeamId",
        "awayTeamDisplayName" }, message = "Either awayTeamId or awayTeamDisplayName must be provided"),
    @AtLeastOneNotNull(fields = { "venueId",
        "venueDisplayName" }, message = "Either venueId or venueDisplayName must be provided"),
    @AtLeastOneNotNull(fields = { "refereeId",
        "refereeDisplayName" }, message = "Either refereeId or refereeDisplayName must be provided")
})
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
  private String venueDisplayName;

  private Long refereeId;
  private String refereeDisplayName;

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
