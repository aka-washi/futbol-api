package com.eagle.futbolapi.features.staff.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.base.validation.AtLeastOneNotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Staff entities.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AtLeastOneNotNull(fields = { "personId",
    "personDisplayName" }, message = "Either personId or personDisplayName must be provided")
public class StaffDto {

  private Long id;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;

  private Long personId;
  private String personDisplayName;

  @NotBlank(message = "Role is required")
  private String role;

  private Long currentTeamId;
  private String currentTeamDisplayName;

  @NotNull(message = "Active flag is required")
  private Boolean active;

}
