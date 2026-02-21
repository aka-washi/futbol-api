package com.eagle.futbolapi.features.venue.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Venue entities.
 * Used for transferring venue/stadium data between the API layer and clients.
 * Contains validation constraints to ensure data integrity.
 *
 * <p>
 * This DTO includes both entity metadata (ID, timestamps, audit fields)
 * and venue-specific information (name, capacity, location, etc).
 *
 * <p>
 * Venues represent stadiums, arenas, or other facilities where football
 * matches are played.
 *
 * @see com.eagle.futbolapi.features.venue.entity.Venue
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VenueDto {

  /** Unique identifier of the venue */
  private Long id;

  /** Timestamp when the venue was created */
  private LocalDateTime createdAt;

  /** Username of the user who created the venue */
  private String createdBy;

  /** Timestamp when the venue was last updated */
  private LocalDateTime updatedAt;

  /** Username of the user who last updated the venue */
  private String updatedBy;

  /**
   * The official name of the venue.
   * Must be between 2 and 100 characters.
   */
  @NotBlank(message = "Venue name is required")
  @Size(min = 2, max = 100, message = "Venue name must be between 2 and 100 characters")
  private String name;

  /**
   * The display name used for UI presentation.
   * Must be between 2 and 100 characters.
   */
  @NotBlank(message = "Display name is required")
  @Size(min = 2, max = 100, message = "Display name must be between 2 and 100 characters")
  private String displayName;

  /** Unique identifier of the country where the venue is located */
  @NotNull(message = "Country ID is required")
  private Long countryId;

  /** Display name of the country where the venue is located */
  private String countryDisplayName;

  /**
   * Maximum capacity of the venue.
   * Represents the number of spectators the venue can accommodate.
   */
  @NotNull(message = "Capacity is required")
  private Integer capacity;

  /**
   * URL to an image of the venue.
   * Maximum 500 characters.
   */
  @Size(max = 500, message = "Image URL cannot exceed 500 characters")
  private String imageUrl;

  /**
   * Type of playing surface.
   * Examples: "Natural Grass", "Artificial Turf", "Hybrid"
   * Maximum 50 characters.
   */
  @Size(max = 50, message = "Surface type cannot exceed 50 characters")
  private String surface;

  /**
   * Physical address of the venue.
   * Maximum 200 characters.
   */
  @Size(max = 200, message = "Address cannot exceed 200 characters")
  private String address;

  /**
   * City where the venue is located.
   * Maximum 100 characters.
   */
  @Size(max = 100, message = "City cannot exceed 100 characters")
  private String city;

  /**
   * Date when the venue was opened/inaugurated.
   */
  private LocalDate opened;

  /**
   * Indicates whether the venue is currently active.
   * True if active, false if closed or inactive.
   */
  private Boolean active;
}
