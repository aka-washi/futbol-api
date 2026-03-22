package com.eagle.futbolapi.features.venue.dto;

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
@AtLeastOneNotNull(fields = { "countryId",
    "countryDisplayName" }, message = "Either countryId or countryDisplayName must be provided")
public class VenueDTO {

  private Long id;

  @NotBlank(message = "Venue name is required")
  @Size(min = 2, max = 100, message = "Venue name must be between 2 and 100 characters")
  private String name;

  @NotBlank(message = "Display name is required")
  @Size(min = 2, max = 100, message = "Display name must be between 2 and 100 characters")
  private String displayName;

  @NotBlank(message = "City is required")
  @Size(min = 2, max = 100, message = "City must be between 2 and 100 characters")
  private String city;

  private Long countryId;
  private String countryDisplayName;

  @NotNull(message = "Capacity is required")
  private Integer capacity;

  private String address;
  private String image;
  private LocalDate opened;

  @Size(max = 100, message = "Surface cannot exceed 100 characters")
  private String surface;

  private Boolean active;

  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;
}
