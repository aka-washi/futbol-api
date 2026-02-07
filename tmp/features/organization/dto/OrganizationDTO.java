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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDTO {

  private Long id;

  @NotBlank(message = "Organization name is required")
  @Size(min = 2, max = 150, message = "Organization name must be between 2 and 150 characters")
  private String name;

  @NotBlank(message = "Display name is required")
  @Size(min = 2, max = 150, message = "Display name must be between 2 and 150 characters")
  private String displayName;

  @Size(max = 20, message = "Abbreviation cannot exceed 20 characters")
  private String abbreviation;

  @NotNull(message = "Founded date is required")
  private LocalDate founded;

  private Long parentOrganizationId;
  private String parentOrganizationDisplayName;

  private Long countryId;
  private String countryDisplayName;

  @Size(max = 200, message = "Headquarters cannot exceed 200 characters")
  private String headquarters;

  @Size(max = 500, message = "Logo URL cannot exceed 500 characters")
  private String logo;

  @Size(max = 500, message = "Website URL cannot exceed 500 characters")
  private String website;

  private String description;

  @NotNull(message = "Organization type is required")
  private String type;

  private LocalDateTime createdAt;
  private String createdBy;

  private LocalDateTime updatedAt;
  private String updatedBy;
}
