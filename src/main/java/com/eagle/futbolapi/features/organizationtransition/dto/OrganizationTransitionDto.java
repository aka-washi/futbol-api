package com.eagle.futbolapi.features.organizationtransition.dto;

import java.time.LocalDateTime;

import com.eagle.futbolapi.features.base.validation.AtLeastOneNotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for OrganizationTransition entities.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AtLeastOneNotNull.List({
    @AtLeastOneNotNull(fields = { "fromOrganizationId",
        "fromOrganizationDisplayName" }, message = "Either fromOrganizationId or fromOrganizationDisplayName must be provided"),
    @AtLeastOneNotNull(fields = { "toOrganizationId",
        "toOrganizationDisplayName" }, message = "Either toOrganizationId or toOrganizationDisplayName must be provided")
})
public class OrganizationTransitionDto {

  private Long id;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;

  private Long fromOrganizationId;
  private String fromOrganizationDisplayName;

  private Long toOrganizationId;
  private String toOrganizationDisplayName;

  private Long effectiveSeasonId;
  private String effectiveSeasonDisplayName;

  private String transitionType;

}
