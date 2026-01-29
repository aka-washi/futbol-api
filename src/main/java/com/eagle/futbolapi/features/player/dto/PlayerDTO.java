package com.eagle.futbolapi.features.player.dto;

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
@AtLeastOneNotNull(fields = { "personId",
    "personDisplayName" }, message = "Either personId or personDisplayName must be provided")
public class PlayerDTO {

  private Long id;

  private Long personId;
  private String personDisplayName;

  @NotNull(message = "Position is required")
  private String position;

  private String preferredFoot;

  private Long currentTeamId;
  private String currentTeamDisplayName;

  private Boolean active;

  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;
}
