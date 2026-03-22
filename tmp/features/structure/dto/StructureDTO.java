package com.eagle.futbolapi.features.structure.dto;

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
public class StructureDTO {

    private Long id;

    @NotBlank(message = "Structure name is required")
    @Size(min = 2, max = 100, message = "Structure name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Display name is required")
    @Size(min = 2, max = 100, message = "Display name must be between 2 and 100 characters")
    private String displayName;

    @NotNull(message = "Structure type is required")
    private String type;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    private String rules;

    private Long pointSystemId;
    private String pointSystemDisplayName;

    private Integer numberOfTeams;
    private Integer numberOfGroups;
    private Integer teamsPerGroup;
    private Boolean hasHomeAndAway;
    private Integer teamsQualifyingForNextStage;

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
