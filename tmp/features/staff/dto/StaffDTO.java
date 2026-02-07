package com.eagle.futbolapi.features.staff.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.base.validation.AtLeastOneNotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AtLeastOneNotNull(fields = { "personId",
    "personDisplayName" }, message = "Either personId or personDisplayName must be provided")
public class StaffDTO {

  private Long id;

  private Long personId;
  private String personDisplayName;

  @NotNull(message = "Role is required")
  private String role;

  private Long currentTeamId;
  private String currentTeamDisplayName;

  private Boolean active;

  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;
}
