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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AtLeastOneNotNull.List({
    @AtLeastOneNotNull(fields = { "competitionId",
        "competitionDisplayName" }, message = "Either competitionId or competitionDisplayName must be provided"),
    @AtLeastOneNotNull(fields = { "structureId",
        "structureDisplayName" }, message = "Either structureId or structureDisplayName must be provided")
})
public class StageDTO {

  private Long id;

  private Long competitionId;
  private String competitionDisplayName;

  private Long structureId;
  private String structureDisplayName;

  @NotBlank(message = "Stage name is required")
  @Size(min = 2, max = 100, message = "Stage name must be between 2 and 100 characters")
  private String name;

  @NotBlank(message = "Display name is required")
  @Size(min = 2, max = 100, message = "Display name must be between 2 and 100 characters")
  private String displayName;

  @NotNull(message = "Order is required")
  private Integer order;

  @NotNull(message = "Start date is required")
  private LocalDate startDate;

  @NotNull(message = "End date is required")
  private LocalDate endDate;

  private String status;

  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;
}
