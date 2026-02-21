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
 * Used for transferring organization data between the API layer and clients.
 * Contains validation constraints to ensure data integrity.
 *
 * <p>
 * This DTO includes both entity metadata (ID, timestamps, audit fields)
 * and organization-specific information (name, type, country, etc).
 *
 * <p>
 * Organizations can be clubs, federations, confederations, or governing bodies
 * in the football ecosystem.
 *
 * @see com.eagle.futbolapi.features.organization.entity.Organization
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDto {

  /** Unique identifier of the organization */
  private Long id;

  /** Timestamp when the organization was created */
  private LocalDateTime createdAt;

  /** Username of the user who created the organization */
  private String createdBy;

  /** Timestamp when the organization was last updated */
  private LocalDateTime updatedAt;

  /** Username of the user who last updated the organization */
  private String updatedBy;

  /**
   * The official name of the organization.
   * Must be between 2 and 100 characters.
   */
  @NotBlank(message = "Organization name is required")
  @Size(min = 2, max = 100, message = "Organization name must be between 2 and 100 characters")
  private String name;

  /**
   * The display name used for UI presentation.
   * Must be between 2 and 100 characters.
   */
  @NotBlank(message = "Display name is required")
  @Size(min = 2, max = 100, message = "Display name must be between 2 and 100 characters")
  private String displayName;

  /**
   * Type of organization.
   * Examples: CLUB, FEDERATION, CONFEDERATION, GOVERNING_BODY
   */
  @NotBlank(message = "Organization type is required")
  private String type;

  /**
   * Short abbreviation for the organization.
   * Maximum 10 characters.
   */
  @Size(max = 10, message = "Abbreviation cannot exceed 10 characters")
  private String abbreviation;

  /** Unique identifier of the country where the organization is based */
  @NotNull(message = "Country ID is required")
  private Long countryId;

  /** Display name of the country where the organization is based */
  private String countryDisplayName;

  /**
   * Date when the organization was founded.
   * Required field.
   */
  @NotNull(message = "Founded date is required")
  private LocalDate founded;

  /** Unique identifier of the parent organization, if any */
  private Long parentOrganizationId;

  /** Display name of the parent organization, if any */
  private String parentOrganizationDisplayName;

  /**
   * URL to the organization's logo image.
   * Maximum 500 characters.
   */
  @Size(max = 500, message = "Logo URL cannot exceed 500 characters")
  private String logo;

  /**
   * Official website URL of the organization.
   * Maximum 200 characters.
   */
  @Size(max = 200, message = "Website URL cannot exceed 200 characters")
  private String website;

  /**
   * Location of the organization's headquarters.
   * Maximum 200 characters.
   */
  @Size(max = 200, message = "Headquarters cannot exceed 200 characters")
  private String headquarters;

  /**
   * Description of the organization.
   * Maximum 1000 characters.
   */
  @Size(max = 1000, message = "Description cannot exceed 1000 characters")
  private String description;
}
