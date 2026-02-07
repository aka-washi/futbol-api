package com.eagle.futbolapi.features.lineup.dto;

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
    @AtLeastOneNotNull(fields = { "matchId",
        "matchDisplayName" }, message = "Either matchId or matchDisplayName must be provided"),
    @AtLeastOneNotNull(fields = { "teamId",
        "teamDisplayName" }, message = "Either teamId or teamDisplayName must be provided"),
    @AtLeastOneNotNull(fields = { "playerId",
        "playerDisplayName" }, message = "Either playerId or playerDisplayName must be provided")
})
public class LineupDTO {

  private Long id;

  private Long matchId;
  private String matchDisplayName;

  private Long teamId;
  private String teamDisplayName;

  private Long playerId;
  private String playerDisplayName;

  @NotNull(message = "Lineup type is required")
  private String type;

  private String position;
  private Integer jerseyNumber;
  private Boolean captain;
  private Integer orderNumber;

  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;
}
