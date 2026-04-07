package com.eagle.futbolapi.features.teamvenue.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.base.enums.VenueDesignation;
import com.eagle.futbolapi.features.base.validation.AtLeastOneNotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for TeamVenue entities.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AtLeastOneNotNull.List({
    @AtLeastOneNotNull(fields = { "teamId",
        "teamDisplayName" }, message = "Either teamId or teamDisplayName must be provided"),
    @AtLeastOneNotNull(fields = { "venueId",
        "venueDisplayName" }, message = "Either venueId or venueDisplayName must be provided")
})
public class TeamVenueDto {

  private Long id;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;

  private Long teamId;
  private String teamDisplayName;

  private Long venueId;
  private String venueDisplayName;

  @NotNull(message = "Venue designation is required")
  private VenueDesignation designation;

  @NotNull(message = "Start date is required")
  private LocalDate startDate;
  @NotNull(message = "End date is required")
  private LocalDate endDate;

}
