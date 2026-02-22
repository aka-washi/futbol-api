package com.eagle.futbolapi.features.group.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Group entities.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {

  private Long id;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;

  @NotNull(message = "Stage ID is required")
  private Long stageId;
  private String stageDisplayName;

  @NotBlank(message = "Group name is required")
  @Size(max = 50, message = "Group name cannot exceed 50 characters")
  private String name;

  @NotBlank(message = "Display name is required")
  @Size(max = 100, message = "Display name cannot exceed 100 characters")
  private String displayName;

  @NotNull(message = "Order is required")
  private Integer order;

  private Integer numberOfTeams;
}
