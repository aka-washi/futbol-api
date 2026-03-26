package com.eagle.futbolapi.features.registration.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
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
@AtLeastOneNotNull.List({
    @AtLeastOneNotNull(fields = { "playerId", "playerDisplayName", "staffId", "staffDisplayName" }, message = "Either player or staff must be provided")
})
public class RegistrationDto {

  private Long id;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;

  @NotBlank(message = "Display name is required")
  private String displayName;

  private Long competitionId;
  private String competitionDisplayName;

  private Long teamId;
  private String teamDisplayName;

  private Long playerId;
  private String playerDisplayName;

  private Long staffId;
  private String staffDisplayName;

  private Integer jerseyNumber;

  @NotNull(message = "Joined date is required")
  private LocalDate joinedDate;

  private LocalDate leftDate;

}
