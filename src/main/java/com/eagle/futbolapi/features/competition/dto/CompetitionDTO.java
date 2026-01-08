package com.eagle.futbolapi.features.competition.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionDTO {

    private Long id;

    private Long seasonId;

    @NotBlank(message = "Competition name is required")
    @Size(min = 2, max = 100, message = "Competition name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Display name is required")
    @Size(min = 2, max = 100, message = "Display name must be between 2 and 100 characters")
    private String displayName;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean active;
    private Integer totalMatchdays;
    private String description;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;

}
