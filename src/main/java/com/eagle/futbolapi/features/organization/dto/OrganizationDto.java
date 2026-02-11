package com.eagle.futbolapi.features.organization.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDto {

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

  private String name;
  @Size(min = 2, max = 100, message = "Display name must be between 2 and 100 characters")
  private String displayName;
  private String type;
  private String abbreviation;
  private Long countryId;
  private String countryDisplayName;
  private LocalDate founded;
  private Long parentOrganizationId;
  private String parentOrganizationDisplayName;
  private String logo;
  private String website;
  private String headquarters;
  private String description;
}
