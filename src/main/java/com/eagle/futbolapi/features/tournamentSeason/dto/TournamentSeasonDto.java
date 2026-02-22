package com.eagle.futbolapi.features.tournamentSeason.dto;

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
 * Data Transfer Object for TournamentSeason entities.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AtLeastOneNotNull.List({
    @AtLeastOneNotNull(fields = { "seasonId",
        "seasonDisplayName" }, message = "Either seasonId or seasonDisplayName must be provided"),
    @AtLeastOneNotNull(fields = { "tournamentId",
        "tournamentDisplayName" }, message = "Either tournamentId or tournamentDisplayName must be provided")
})
public class TournamentSeasonDto {

  private Long id;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;

  private Long seasonId;
  private String seasonDisplayName;

  private Long tournamentId;
  private String tournamentDisplayName;

  @NotBlank(message = "Display name is required")
  @Size(min = 2, max = 100, message = "Display name must be between 2 and 100 characters")
  private String displayName;

  @NotNull(message = "Start date is required")
  private LocalDate startDate;

  @NotNull(message = "End date is required")
  private LocalDate endDate;

  private String status;
  private Boolean hasRelegation;
  private Boolean hasPromotion;
  private Integer numberOfTeams;
  private String description;
}
