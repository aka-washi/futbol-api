package com.eagle.futbolapi.features.structure.mapper;

import org.springframework.stereotype.Component;

import com.eagle.futbolapi.features.pointsystem.repository.PointSystemRepository;
import com.eagle.futbolapi.features.structure.dto.StructureDTO;
import com.eagle.futbolapi.features.structure.entity.Structure;
import com.eagle.futbolapi.features.structure.entity.StructureType;

@Component
public class StructureMapper {

    private final PointSystemRepository pointSystemRepository;

    public StructureMapper(PointSystemRepository pointSystemRepository) {
        this.pointSystemRepository = pointSystemRepository;
    }

    public StructureDTO toStructureDTO(Structure structure) {
        if (structure == null) {
            return null;
        }

        return StructureDTO.builder()
                .id(structure.getId())
                .name(structure.getName())
                .displayName(structure.getDisplayName())
                .type(structure.getType() != null ? structure.getType().name() : null)
                .description(structure.getDescription())
                .rules(structure.getRules())
                .pointSystemId(structure.getPointSystem() != null ? structure.getPointSystem().getId() : null)
                .pointSystemDisplayName(
                        structure.getPointSystem() != null ? structure.getPointSystem().getDisplayName() : null)
                .numberOfTeams(structure.getNumberOfTeams())
                .numberOfGroups(structure.getNumberOfGroups())
                .teamsPerGroup(structure.getTeamsPerGroup())
                .hasHomeAndAway(structure.getHasHomeAndAway())
                .teamsQualifyingForNextStage(structure.getTeamsQualifyingForNextStage())
                .createdAt(structure.getCreatedAt())
                .createdBy(structure.getCreatedBy())
                .updatedAt(structure.getUpdatedAt())
                .updatedBy(structure.getUpdatedBy())
                .build();
    }

    public Structure toStructure(StructureDTO dto) {
        if (dto == null) {
            return null;
        }

        var builder = Structure.builder()
                .id(dto.getId())
                .name(dto.getName())
                .displayName(dto.getDisplayName())
                .description(dto.getDescription())
                .rules(dto.getRules())
                .numberOfTeams(dto.getNumberOfTeams())
                .numberOfGroups(dto.getNumberOfGroups())
                .teamsPerGroup(dto.getTeamsPerGroup())
                .hasHomeAndAway(dto.getHasHomeAndAway())
                .teamsQualifyingForNextStage(dto.getTeamsQualifyingForNextStage())
                .createdAt(dto.getCreatedAt())
                .createdBy(dto.getCreatedBy())
                .updatedAt(dto.getUpdatedAt())
                .updatedBy(dto.getUpdatedBy());

        if (dto.getType() != null) {
            try {
                builder.type(StructureType.valueOf(dto.getType().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid structure type: " + dto.getType());
            }
        }

        if (dto.getPointSystemId() != null) {
            var pointSystem = pointSystemRepository.findById(dto.getPointSystemId())
                    .orElse(null);
            builder.pointSystem(pointSystem);
        }

        return builder.build();
    }
}
