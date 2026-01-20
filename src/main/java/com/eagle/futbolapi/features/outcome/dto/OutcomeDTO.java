package com.eagle.futbolapi.features.outcome.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutcomeDTO {

  private Long id;

  @NotNull(message = "Competition is required")
  private Long competitionId;
  private String competitionDisplayName;

  private Long teamId;
  private String teamDisplayName;

  private Long playerId;
  private String playerDisplayName;

  private Long staffId;
  private String staffDisplayName;

  @NotNull(message = "Outcome type is required")
  private String outcomeType;

  @Size(max = 500, message = "Description cannot exceed 500 characters")
  private String description;

  private BigDecimal value;

  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;
}
