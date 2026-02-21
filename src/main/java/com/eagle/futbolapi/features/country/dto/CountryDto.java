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
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryDto {

  private Long id;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;


  @NotBlank(message = "Country name is required")
  @Size(min = 2, max = 100, message = "Country name must be between 2 and 100 characters")
  private String name;

  @NotBlank(message = "Display name is required")
  @Size(min = 2, max = 100, message = "Display name must be between 2 and 100 characters")
  private String displayName;

  @Size(max = 2, message = "Country code cannot exceed 2 characters")
  private String code;


  @Size(max = 3, message = "ISO code cannot exceed 3 characters")
  private String isoCode;

  @Size(max = 500, message = "Flag URL cannot exceed 500 characters")
  private String flagUrl;

}
