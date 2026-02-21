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
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VenueDto {
  private Long id;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;
  @NotBlank(message = "Venue name is required")
  @Size(min = 2, max = 100, message = "Venue name must be between 2 and 100 characters")
  private String name;
  @NotBlank(message = "Display name is required")
  @Size(min = 2, max = 100, message = "Display name must be between 2 and 100 characters")
  private String displayName;
  @NotNull(message = "Country ID is required")
  private Long countryId;
  private String countryDisplayName;
  @NotNull(message = "Capacity is required")
  private Integer capacity;
  @Size(max = 500, message = "Image URL cannot exceed 500 characters")
  private String imageUrl;
  @Size(max = 50, message = "Surface type cannot exceed 50 characters")
  private String surface;
  @Size(max = 200, message = "Address cannot exceed 200 characters")
  private String address;
  @Size(max = 100, message = "City cannot exceed 100 characters")
  private String city;

  /**
   * Date when the venue was opened/inaugurated.
   */
  private LocalDate opened;

  /**
 * Indicates whether the venue is currently active.
 */
  private Boolean active;
}
