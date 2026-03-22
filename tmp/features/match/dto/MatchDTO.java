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
  private String name;
  private String displayName;

  private Long matchdayId;
  private String matchdayDisplayName;

  private Long homeTeamId;
  private String homeTeamDisplayName;

  private Long awayTeamId;
  private String awayTeamDisplayName;

  private Long venueId;
  private String venueDisplayName;

  private Long refereeId;
  private String refereeDisplayName;

  @NotNull(message = "Match date is required")
  private LocalDate scheduledDate;
  private LocalDateTime kickoffTime;
  private String status;
  private Integer homeScore;
  private Integer awayScore;
  private Integer homeHalfTimeScore;
  private Integer awayHalfTimeScore;
  private Boolean extraTimeAllowed;
  private Boolean penaltyShootoutAllowed;
  private Integer homeExtraTimeScore;
  private Integer awayExtraTimeScore;
  private Integer homePenaltyScore;
  private Integer awayPenaltyScore;
  private Integer attendance;
  private String weatherConditions;
  private String notes;

  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;
}
