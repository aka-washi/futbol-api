package com.eagle.futbolapi.features.tournament.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.eagle.futbolapi.features.base.validation.AtLeastOneNotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Tournament entities.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AtLeastOneNotNull(fields = { "organizationId", "organizationDisplayName" },
        message = "Either organizationId or organizationDisplayName must be provided")
public class TournamentDto {
    private Long id;
    private Long organizationId;
    private String organizationDisplayName;
    @NotBlank(message = "Tournament name is required")
    @Size(min = 2, max = 100, message = "Tournament name must be between 2 and 100 characters")
    private String name;
    @NotBlank(message = "Display name is required")
    @Size(min = 2, max = 150, message = "Display name must be between 2 and 150 characters")
    private String displayName;
    @NotBlank(message = "Tournament type is required")
    private String type;
    @NotBlank(message = "Age category is required")
    private String ageCategory;
    @NotNull(message = "Level is required")
    private Integer level;
    private Long relegationToId;
    private String relegationToDisplayName;
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
    @Size(max = 500, message = "Logo URL cannot exceed 500 characters")
    private String logo;

    /**
 * Year when the tournament was founded.
 */
    private Integer foundedYear;
    @Size(max = 200, message = "Website URL cannot exceed 200 characters")
    private String website;

    /**
 * Indicates whether the tournament is currently active.
 */
    private Boolean active;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
