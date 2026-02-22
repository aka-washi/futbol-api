package com.eagle.futbolapi.features.lineupMember.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for LineupMember entities.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LineupMemberDto {

  private Long id;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;

  @NotNull(message = "Lineup ID is required")
  private Long lineupId;
  private String lineupDisplayName;

  @NotBlank(message = "Person type is required")
  private String personType;

  private Long playerId;
  private String playerDisplayName;

  private Long staffId;
  private String staffDisplayName;

  @NotBlank(message = "Role type is required")
  private String roleType;

  @Size(max = 50, message = "Position cannot exceed 50 characters")
  private String position;

  private Integer jerseyNumber;

  private boolean captain;

  private Integer orderNum;
}
