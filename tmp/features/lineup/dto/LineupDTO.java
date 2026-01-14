package com.eagle.futbolapi.features.lineup.dto;

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
public class LineupDTO {

    private Long id;

    @NotNull(message = "Match is required")
    private Long matchId;
    private String matchDisplayName;

    @NotNull(message = "Team is required")
    private Long teamId;
    private String teamDisplayName;

    @NotNull(message = "Player is required")
    private Long playerId;
    private String playerDisplayName;

    @NotNull(message = "Lineup type is required")
    private String lineupType;

    private Integer jerseyNumber;
    private String position;
    private Integer minuteIn;
    private Integer minuteOut;
    private Boolean isCaptain;

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
