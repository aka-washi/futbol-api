package com.eagle.futbolapi.features.season.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Season entities.
 * Used for transferring season data between the API layer and clients.
 * Contains validation constraints to ensure data integrity.
 *
 * <p>
 * This DTO includes both entity metadata (ID, timestamps, audit fields)
 * and season-specific information (name and display name).
 *
 * <p>
 * Seasons typically represent time periods in football competitions,
 * such as "2023-2024" or "2024/25".
 *
 * @see com.eagle.futbolapi.features.season.entity.Season
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeasonDto {

  /** Unique identifier of the season */
  private Long id;

  /** Timestamp when the season was created */
  private LocalDateTime createdAt;

  /** Username of the user who created the season */
  private String createdBy;

  /** Timestamp when the season was last updated */
  private LocalDateTime updatedAt;

  /** Username of the user who last updated the season */
  private String updatedBy;

  /**
   * The official name of the season.
   * Must be between 2 and 100 characters.
   * Examples: "2023-2024", "2024/25"
   */
  @NotBlank(message = "Season name is required")
  @Size(min = 2, max = 100, message = "Season name must be between 2 and 100 characters")
  private String name;

  /**
   * The display name used for UI presentation.
   * Must be between 2 and 100 characters.
   * Can be formatted differently from name for better readability.
   */
  @NotBlank(message = "Display name is required")
  @Size(min = 2, max = 100, message = "Display name must be between 2 and 100 characters")
  private String displayName;

}
