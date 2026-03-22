package com.eagle.futbolapi.features.stage.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.eagle.futbolapi.features.base.validation.AtLeastOneNotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Stage entities.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AtLeastOneNotNull(fields = { "competitionId",
    "competitionDisplayName" }, message = "Either competitionId or competitionDisplayName must be provided")
public class StageDto {

  private Long id;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;

  @NotBlank(message = "Display name is required")
  @Size(min = 2, max = 100, message = "Display name must be between 2 and 100 characters")
  private String displayName;

  private Long competitionId;
  private String competitionDisplayName;

  private Long stageFormatId;
  private String stageFormatDisplayName;

  @NotNull(message = "Order is required")
  private Integer order;

  private String status;
  private LocalDate startDate;
  private LocalDate endDate;
}
