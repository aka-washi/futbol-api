package com.eagle.futbolapi.features.pointsystem.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Point System entities.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointSystemDto {
  private Long id;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;
  @NotBlank(message = "Point System name is required")
  @Size(min = 2, max = 100, message = "Point System name must be between 2 and 100 characters")
  private String name;
  @NotBlank(message = "Display name is required")
  @Size(min = 2, max = 100, message = "Display name must be between 2 and 100 characters")
  private String displayName;
  @Size(max = 1000)
  private String description;
  @Min(0)
  private Integer pointsForWin;
  @Min(0)
  private Integer pointsForDraw;
  @Min(0)
  private Integer pointsForLoss;

  /**
 * Points awarded for winning after a penalty shootout.
 */
  private Integer pointsForWinOnPenalties;

  /**
 * Points awarded for losing after a penalty shootout.
 */
  private Integer pointsForLossOnPenalties;

  /**
 * Points awarded for winning by opponent forfeit.
 */
  private Integer pointsForForfeitWin;

  /**
 * Points awarded for losing by forfeit.
 */
  private Integer pointsForForfeitLoss;

}
