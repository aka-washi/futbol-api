package com.eagle.futbolapi.features.qualificationoutcome.dto;

import java.time.LocalDateTime;

import com.eagle.futbolapi.features.base.validation.AtLeastOneNotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for QualificationOutcome entities.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AtLeastOneNotNull.List({
    @AtLeastOneNotNull(fields = { "sourceCompetitionId",
        "sourceCompetitionDisplayName" }, message = "Either sourceCompetitionId or sourceCompetitionDisplayName must be provided"),
    @AtLeastOneNotNull(fields = { "targetCompetitionId",
        "targetCompetitionDisplayName" }, message = "Either targetCompetitionId or targetCompetitionDisplayName must be provided"),
    @AtLeastOneNotNull(fields = { "teamId",
        "teamDisplayName" }, message = "Either teamId or teamDisplayName must be provided")
})
public class QualificationOutcomeDto {

  private Long id;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;

  private Long sourceCompetitionId;
  private String sourceCompetitionDisplayName;

  private Long targetCompetitionId;
  private String targetCompetitionDisplayName;

  private Long teamId;
  private String teamDisplayName;

  private String qualificationType;
}
