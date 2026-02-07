package com.eagle.futbolapi.features.matchevent.dto;

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
public class MatchEventDTO {

  private Long id;

  private Long matchId;
  private String matchDisplayName;

  private Long teamId;
  private String teamDisplayName;

  private Long playerId;
  private String playerDisplayName;

  private Long assistPlayerId;
  private String assistPlayerDisplayName;

  private Long substitutePlayerId;
  private String substitutePlayerDisplayName;

  @NotNull(message = "Type is required")
  private String type;

  private String period;

  @NotNull(message = "Minute is required")
  private Integer minute;
  private Integer extraMinute;

  private String description;

  private String videoUrl;

  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;
}
