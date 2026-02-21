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
 * Used for transferring tournament data between the API layer and clients.
 * Contains validation constraints to ensure data integrity.
 *
 * <p>
 * This DTO includes both entity metadata (ID, timestamps, audit fields)
 * and tournament-specific information (name, type, organization, etc).
 *
 * <p>
 * Tournaments represent football competitions such as leagues, cups,
 * and championship series organized by various football organizations.
 *
 * @see com.eagle.futbolapi.features.tournament.entity.Tournament
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AtLeastOneNotNull(fields = { "organizationId", "organizationDisplayName" },
        message = "Either organizationId or organizationDisplayName must be provided")
public class TournamentDto {

    /** Unique identifier of the tournament */
    private Long id;

    /** Unique identifier of the organizing body */
    private Long organizationId;

    /** Display name of the organizing body */
    private String organizationDisplayName;

    /**
     * The official name of the tournament.
     * Must be between 2 and 100 characters.
     * Examples: "Premier League", "UEFA Champions League"
     */
    @NotBlank(message = "Tournament name is required")
    @Size(min = 2, max = 100, message = "Tournament name must be between 2 and 100 characters")
    private String name;

    /**
     * The display name used for UI presentation.
     * Must be between 2 and 150 characters.
     */
    @NotBlank(message = "Display name is required")
    @Size(min = 2, max = 150, message = "Display name must be between 2 and 150 characters")
    private String displayName;

    /**
     * Type of tournament.
     * Examples: LEAGUE, CUP, SUPER_CUP, PLAYOFF
     */
    @NotBlank(message = "Tournament type is required")
    private String type;

    /**
     * Age category of the tournament.
     * Examples: PROFESSIONAL, U21, U19, U17
     */
    @NotBlank(message = "Age category is required")
    private String ageCategory;

    /**
     * Level/tier of the tournament in the football pyramid.
     * Lower numbers represent higher tiers (e.g., 1 = top division).
     */
    @NotNull(message = "Level is required")
    private Integer level;

    /** Unique identifier of the tournament to which teams are relegated */
    private Long relegationToId;

    /** Display name of the tournament to which teams are relegated */
    private String relegationToDisplayName;

    /**
     * Description of the tournament.
     * Maximum 500 characters.
     */
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    /**
     * URL to the tournament's logo image.
     * Maximum 500 characters.
     */
    @Size(max = 500, message = "Logo URL cannot exceed 500 characters")
    private String logo;

    /**
     * Year when the tournament was founded.
     * Examples: 1992, 1955
     */
    private Integer foundedYear;

    /**
     * Official website URL of the tournament.
     * Maximum 200 characters.
     */
    @Size(max = 200, message = "Website URL cannot exceed 200 characters")
    private String website;

    /**
     * Indicates whether the tournament is currently active.
     * True if active, false if discontinued or suspended.
     */
    private Boolean active;

    /** Timestamp when the tournament was created */
    private LocalDateTime createdAt;

    /** Username of the user who created the tournament */
    private String createdBy;

    /** Timestamp when the tournament was last updated */
    private LocalDateTime updatedAt;

    /** Username of the user who last updated the tournament */
    private String updatedBy;
}
