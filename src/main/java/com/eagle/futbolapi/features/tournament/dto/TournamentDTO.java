package com.eagle.futbolapi.features.tournament.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TournamentDTO {

    private Long id;

    @NotNull(message = "Organization is required")
    private Long organizationId;
    private String organizationDisplayName;

    @NotBlank(message = "Tournament name is required")
    @Size(min = 2, max = 100, message = "Tournament name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Display name is required")
    @Size(min = 2, max = 150, message = "Display name must be between 2 and 150 characters")
    private String displayName;

    private String type;

    private String ageCategory;

    private Integer level;

    private Long relegationToId;
    private String relegationToDisplayName;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    private String logo;

    @NotNull(message = "Founded year is required")
    private Integer foundedYear;

    private Boolean active;

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
