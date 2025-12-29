package com.eagle.futbolapi.features.pointsystem.dto;

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
public class PointSystemDTO {

    private Long id;

    @NotBlank(message = "Point System name is required")
    @Size(min = 2, max = 100, message = "Point System name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Display name is required")
    @Size(min = 2, max = 100, message = "Display name must be between 2 and 100 characters")
    private String displayName;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Points for win is required")
    private Integer pointsForWin;

    @NotNull(message = "Points for draw is required")
    private Integer pointsForDraw;

    @NotNull(message = "Points for loss is required")
    private Integer pointsForLoss;

    private Integer pointsForWinOnPenalties;
    private Integer pointsForLossOnPenalties;

    @NotNull(message = "Active status is required")
    private Boolean isActive;

    @NotNull(message = "Created at timestamp is required")
    private LocalDateTime createdAt;
    @NotNull(message = "Created by is required")
    private String createdBy;

    private LocalDateTime updatedAt;
    private String updatedBy;
}
