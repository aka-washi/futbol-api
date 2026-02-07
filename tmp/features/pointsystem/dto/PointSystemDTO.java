package com.eagle.futbolapi.features.pointsystem.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointSystemDTO {

  private Long id;

  @NotBlank(message = "Point System name is required")
  @Size(min = 2, max = 100, message = "Point System name must be between 2 and 100 characters")
  private String name;

  @NotBlank(message = "Display name is required")
  @Size(min = 2, max = 100, message = "Display name must be between 2 and 100 characters")
  private String displayName;

  @Size(max = 500, message = "Description cannot exceed 500 characters")
  private String description;

  private Integer pointsForWin;
  private Integer pointsForDraw;
  private Integer pointsForLoss;

  private Integer pointsForWinOnPenalties;
  private Integer pointsForLossOnPenalties;

  private Boolean active;

  private LocalDateTime createdAt;
  private String createdBy;

  private LocalDateTime updatedAt;
  private String updatedBy;
}
