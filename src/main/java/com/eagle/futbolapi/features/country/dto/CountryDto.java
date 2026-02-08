package com.eagle.futbolapi.features.country.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Country entities.
 * Used for transferring country data between the API layer and clients.
 * Contains validation constraints to ensure data integrity.
 * 
 * <p>
 * This DTO includes both entity metadata (ID, timestamps, audit fields)
 * and country-specific information (name, codes, flag URL).
 * 
 * @see com.eagle.futbolapi.features.country.entity.Country
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryDto {

  /** Unique identifier of the country */
  private Long id;

  /** Timestamp when the country was created */
  private LocalDateTime createdAt;

  /** Username of the user who created the country */
  private String createdBy;

  /** Timestamp when the country was last updated */
  private LocalDateTime updatedAt;

  /** Username of the user who last updated the country */
  private String updatedBy;

  /**
   * The official name of the country.
   * Must be between 2 and 100 characters.
   */
  @NotBlank(message = "Country name is required")
  @Size(min = 2, max = 100, message = "Country name must be between 2 and 100 characters")
  private String name;

  /**
   * The display name used for UI presentation.
   * Must be between 2 and 100 characters.
   */
  @NotBlank(message = "Display name is required")
  @Size(min = 2, max = 100, message = "Display name must be between 2 and 100 characters")
  private String displayName;

  /**
   * Short country code (e.g., "US", "GB").
   * Maximum 2 characters.
   */
  @Size(max = 2, message = "Country code cannot exceed 2 characters")
  private String code;

  /**
   * ISO 3166-1 alpha-3 country code (e.g., "USA", "GBR").
   * Maximum 3 characters.
   */
  @Size(max = 3, message = "ISO code cannot exceed 3 characters")
  private String isoCode;

  /**
   * URL to the country's flag image.
   * Maximum 500 characters.
   */
  @Size(max = 500, message = "Flag URL cannot exceed 500 characters")
  private String flagUrl;

}
