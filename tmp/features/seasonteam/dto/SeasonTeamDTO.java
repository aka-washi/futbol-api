package com.eagle.futbolapi.features.seasonteam.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeasonTeamDTO {

    private Long id;

    @NotNull(message = "Season is required")
    private Long seasonId;
    private String seasonDisplayName;

    @NotNull(message = "Team is required")
    private Long teamId;
    private String teamDisplayName;

    @NotNull(message = "Joined date is required")
    private LocalDate joinedDate;

    private LocalDate leftDate;
    private Boolean active;

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
