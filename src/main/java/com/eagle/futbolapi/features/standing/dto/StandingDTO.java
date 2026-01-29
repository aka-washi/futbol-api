package com.eagle.futbolapi.features.standing.dto;

import java.time.LocalDateTime;

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
    @AtLeastOneNotNull(fields = { "stageId",
        "stageDisplayName" }, message = "Either stageId or stageDisplayName must be provided"),
    @AtLeastOneNotNull(fields = { "teamId",
        "teamDisplayName" }, message = "Either teamId or teamDisplayName must be provided")
})
public class StandingDTO {

  private Long id;

  private Long stageId;
  private String stageDisplayName;

  private Long teamId;
  private String teamDisplayName;

  private Integer position;
  private Integer played;
  private Integer won;
  private Integer drawn;
  private Integer lost;
  private Integer goalsFor;
  private Integer goalsAgainst;
  private Integer goalDifference;
  private Integer points;
  private String form;
  private String notes;

  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;
}
