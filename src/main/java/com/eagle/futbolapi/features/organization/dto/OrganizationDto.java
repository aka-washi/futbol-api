package com.eagle.futbolapi.features.organization.dto;

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
 * Data Transfer Object for Organization entities.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDto {
  
  private Long id;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;

  @NotBlank(message = "Organization name is required")
  @Size(min = 2, max = 100, message = "Organization name must be between 2 and 100 characters")
  private String name;
  @NotBlank(message = "Display name is required")
  @Size(min = 2, max = 100, message = "Display name must be between 2 and 100 characters")
  private String displayName;
  @NotBlank(message = "Organization type is required")
  private String type;
  @Size(max = 10, message = "Abbreviation cannot exceed 10 characters")
  private String abbreviation;
  private Long countryId;
  private String countryDisplayName;
  @NotNull(message = "Founded date is required")
  private LocalDate founded;
  private Long parentOrganizationId;
  private String parentOrganizationDisplayName;
  @Size(max = 500, message = "Logo URL cannot exceed 500 characters")
  private String logo;
  @Size(max = 200, message = "Website URL cannot exceed 200 characters")
  private String website;
  @Size(max = 200, message = "Headquarters cannot exceed 200 characters")
  private String headquarters;
  @Size(max = 1000, message = "Description cannot exceed 1000 characters")
  private String description;
  private LocalDate startDate;
  private LocalDate endDate;
}
