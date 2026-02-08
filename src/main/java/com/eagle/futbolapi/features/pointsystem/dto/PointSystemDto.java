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
 * Used for transferring point system data between the API layer and clients.
 * Contains validation constraints to ensure data integrity.
 *
 * <p>
 * This DTO includes both entity metadata (ID, timestamps, audit fields)
 * and point system-specific configuration for awarding points based on
 * different match outcomes.
 *
 * <p>
 * Point systems define how points are awarded for wins, draws, losses,
 * penalty outcomes, and forfeits in football competitions.
 *
 * @see com.eagle.futbolapi.features.pointsystem.entity.PointSystem
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointSystemDto {

  /** Unique identifier of the point system */
  private Long id;

  /** Timestamp when the point system was created */
  private LocalDateTime createdAt;

  /** Username of the user who created the point system */
  private String createdBy;

  /** Timestamp when the point system was last updated */
  private LocalDateTime updatedAt;

  /** Username of the user who last updated the point system */
  private String updatedBy;

  /**
   * The unique name identifier for the point system.
   * Must be between 2 and 100 characters.
   * Examples: "standard", "three_points", "two_point_system"
   */
  @NotBlank(message = "Point System name is required")
  @Size(min = 2, max = 100, message = "Point System name must be between 2 and 100 characters")
  private String name;

  /**
   * The display name used for UI presentation.
   * Must be between 2 and 100 characters.
   * Examples: "Standard 3-Point System", "Classic 2-Point System"
   */
  @NotBlank(message = "Display name is required")
  @Size(min = 2, max = 100, message = "Display name must be between 2 and 100 characters")
  private String displayName;

  /**
   * Optional description explaining when and how this point system should be
   * used.
   * Maximum 1000 characters.
   */
  @Size(max = 1000)
  private String description;

  /**
   * Points awarded for a regular win.
   * Must be non-negative. Typically 3 in modern football.
   */
  @Min(0)
  private int pointsForWin;

  /**
   * Points awarded for a draw.
   * Must be non-negative. Typically 1 in modern football.
   */
  @Min(0)
  private int pointsForDraw;

  /**
   * Points awarded for a loss.
   * Must be non-negative. Typically 0 in modern football.
   */
  @Min(0)
  private int pointsForLoss;

  /**
   * Points awarded for winning after a penalty shootout.
   * Used in knockout competitions where draws are resolved.
   */
  private int pointsForWinOnPenalties;

  /**
   * Points awarded for losing after a penalty shootout.
   * Used in knockout competitions where draws are resolved.
   */
  private int pointsForLossOnPenalties;

  /**
   * Points awarded for winning by opponent forfeit.
   * Typically the same as a regular win or higher.
   */
  private int pointsForForfeitWin;

  /**
   * Points awarded for losing by forfeit.
   * Often 0 or negative to penalize the forfeiting team.
   */
  private int pointsForForfeitLoss;

}
