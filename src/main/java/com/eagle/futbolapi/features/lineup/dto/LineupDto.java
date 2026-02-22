package com.eagle.futbolapi.features.lineup.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

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
        "teamDisplayName" }, message = "Either teamId or teamDisplayName must be provided")
})
public class LineupDto {

  private Long id;

  @NotBlank(message = "Display name is required")
  @Size(min = 2, max = 100, message = "Display name must be between 2 and 100 characters")
  private String displayName;

  private Long matchId;
  private String matchDisplayName;

  private Long teamId;
  private String teamDisplayName;

  private String formation;

  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;
}
