package com.eagle.futbolapi.features.rosterentry.dto;

import java.time.LocalDate;
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
@AtLeastOneNotNull.List({
        @AtLeastOneNotNull(fields = { "seasonId",
                "seasonDisplayName" }, message = "Either seasonId or seasonDisplayName must be provided"),
        @AtLeastOneNotNull(fields = { "teamId",
                "teamDisplayName" }, message = "Either teamId or teamDisplayName must be provided")
})
public class RosterEntryDTO {

    private Long id;

    private Long seasonId;
    private String seasonDisplayName;

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
    private Boolean active;

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
