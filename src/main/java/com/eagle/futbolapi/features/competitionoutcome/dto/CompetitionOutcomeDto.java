package com.eagle.futbolapi.features.competitionoutcome.dto;

import java.time.LocalDateTime;

import com.eagle.futbolapi.features.base.validation.AtLeastOneNotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Competition entities.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AtLeastOneNotNull.List({
    @AtLeastOneNotNull(fields = { "competitionId",
        "competitionDisplayName" }, message = "Either competitionId or competitionDisplayName must be provided"),
    @AtLeastOneNotNull(fields = { "teamId",
        "teamDisplayName" }, message = "Either teamId or teamDisplayName must be provided")
})
public class CompetitionOutcomeDto {

  private Long id;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;

  private Long competitionId;
  private String competitionDisplayName;

  private Long teamId;
  private String teamDisplayName;

  private Integer finalPosition;
  private Integer points;
  private String outcomeType;
  private String notes;

}
