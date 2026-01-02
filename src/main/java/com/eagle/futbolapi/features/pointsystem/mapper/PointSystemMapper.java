package com.eagle.futbolapi.features.pointsystem.mapper;

import org.springframework.stereotype.Component;

import com.eagle.futbolapi.features.pointsystem.dto.PointSystemDTO;
import com.eagle.futbolapi.features.pointsystem.entity.PointSystem;

@Component
public class PointSystemMapper {

    public PointSystemDTO toPointSystemDTO(PointSystem pointSystem) {
        if (pointSystem == null) {
            return null;
        }
        return PointSystemDTO.builder()
                .id(pointSystem.getId())
                .name(pointSystem.getName())
                .displayName(pointSystem.getDisplayName())
                .description(pointSystem.getDescription())
                .pointsForWin(pointSystem.getPointsForWin())
                .pointsForDraw(pointSystem.getPointsForDraw())
                .pointsForLoss(pointSystem.getPointsForLoss())
                .pointsForWinOnPenalties(pointSystem.getPointsForWinOnPenalties())
                .pointsForLossOnPenalties(pointSystem.getPointsForLossOnPenalties())
                .isActive(pointSystem.getIsActive())
                .createdAt(pointSystem.getCreatedAt())
                .createdBy(pointSystem.getCreatedBy())
                .updatedAt(pointSystem.getUpdatedAt())
                .updatedBy(pointSystem.getUpdatedBy())
                .build();
    }

    public PointSystem toPointSystem(PointSystemDTO dto) {
        if (dto == null) {
            return null;
        }
        var builder = PointSystem.builder()
                .id(dto.getId())
                .name(dto.getName())
                .displayName(dto.getDisplayName())
                .description(dto.getDescription())
                .pointsForWinOnPenalties(dto.getPointsForWinOnPenalties())
                .pointsForLossOnPenalties(dto.getPointsForLossOnPenalties())
                .createdAt(dto.getCreatedAt())
                .createdBy(dto.getCreatedBy())
                .updatedAt(dto.getUpdatedAt())
                .updatedBy(dto.getUpdatedBy());

        // Only set fields with default values if they're not null, allowing defaults to be used
        if (dto.getPointsForWin() != null) {
            builder.pointsForWin(dto.getPointsForWin());
        }
        if (dto.getPointsForDraw() != null) {
            builder.pointsForDraw(dto.getPointsForDraw());
        }
        if (dto.getPointsForLoss() != null) {
            builder.pointsForLoss(dto.getPointsForLoss());
        }
        if (dto.getIsActive() != null) {
            builder.isActive(dto.getIsActive());
        }

        return builder.build();
    }

}
