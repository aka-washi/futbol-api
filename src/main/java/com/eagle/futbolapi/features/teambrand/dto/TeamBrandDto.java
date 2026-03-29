package com.eagle.futbolapi.features.teambrand.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for TeamBrand entities.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamBrandDto {

  private Long id;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;

  private Long teamId;
  private String teamDisplayName;

  private String name;
  private String displayName;
  private Set<String> nicknames;
  private String abbreviation;
  private String primaryColor;
  private String secondaryColor;
  private String logoUrl;
  private String websiteUrl;
  private LocalDate startDate;
  private LocalDate endDate;
}
