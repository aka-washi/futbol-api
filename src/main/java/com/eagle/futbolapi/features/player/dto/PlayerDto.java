package com.eagle.futbolapi.features.player.dto;

import java.time.LocalDateTime;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.eagle.futbolapi.features.person.dto.PersonDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Player entities.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {

  private Long id;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;

  @Valid
  @NotNull(message = "Person is required")
  private PersonDto person;

  @NotBlank(message = "Position is required")
  private String position;

  private String preferredFoot;

  private Long currentTeamId;
  private String currentTeamDisplayName;

  @NotNull(message = "Active flag is required")
  private Boolean active;

}
