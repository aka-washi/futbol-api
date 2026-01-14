package com.eagle.futbolapi.features.matchevent.dto;

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
public class MatchEventDTO {

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

    @NotNull(message = "Event type is required")
    private String eventType;

    @NotNull(message = "Minute is required")
    private Integer minute;

    private String period;
    private String description;

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
