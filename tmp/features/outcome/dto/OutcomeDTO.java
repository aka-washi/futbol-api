package com.eagle.futbolapi.features.outcome.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
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
@AtLeastOneNotNull(fields = { "competitionId",
    "competitionDisplayName" }, message = "Either competitionId or competitionDisplayName must be provided")
public class OutcomeDTO {

  private Long id;

  @NotNull(message = "Competition is required")
  private Long competitionId;
  private String competitionDisplayName;

  private Long teamId;
  private String teamDisplayName;

  private Long playerId;
  private String playerPersonDisplayName;

  private Long staffId;
  private String staffPersonDisplayName;

  @NotNull(message = "Outcome type is required")
  private String outcomeType;

  private Integer position;

  private BigDecimal value;

  @Size(max = 500, message = "Notes cannot exceed 500 characters")
  private String notes;

  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;
}
