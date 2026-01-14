package com.eagle.futbolapi.features.stage.dto;

import java.time.LocalDate;
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
public class StageDTO {

    private Long id;

    @NotNull(message = "Competition is required")
    private Long competitionId;
    private String competitionDisplayName;

    @NotNull(message = "Structure is required")
    private Long structureId;
    private String structureDisplayName;

    @NotBlank(message = "Stage name is required")
    @Size(min = 2, max = 100, message = "Stage name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Display name is required")
    @Size(min = 2, max = 100, message = "Display name must be between 2 and 100 characters")
    private String displayName;

    @NotNull(message = "Order is required")
    private Integer order;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    private String status;

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
