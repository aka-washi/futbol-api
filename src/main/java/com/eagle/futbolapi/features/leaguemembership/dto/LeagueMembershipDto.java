package com.eagle.futbolapi.features.leaguemembership.dto;

import java.time.LocalDateTime;

import com.eagle.futbolapi.features.base.validation.AtLeastOneNotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for LeagueMembership entities.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AtLeastOneNotNull.List({
    @AtLeastOneNotNull(fields = { "leagueId",
        "leagueDisplayName" }, message = "Either leagueId or leagueDisplayName must be provided"),
    @AtLeastOneNotNull(fields = { "memberId",
        "memberDisplayName" }, message = "Either memberId or memberDisplayName must be provided")
})
public class LeagueMembershipDto {


  private Long id;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;

  private Long leagueId;
  private String leagueDisplayName;

  private Long memberId;
  private String memberDisplayName;

  private String membershipStatus;

  private long startSeasonId;
  private String startSeasonDisplayName;

  private long endSeasonId;
  private String endSeasonDisplayName;
}
