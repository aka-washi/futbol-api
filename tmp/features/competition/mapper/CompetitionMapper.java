package com.eagle.futbolapi.features.competition.mapper;

import org.springframework.stereotype.Component;

import com.eagle.futbolapi.features.competition.dto.CompetitionDTO;
import com.eagle.futbolapi.features.competition.entity.Competition;
import com.eagle.futbolapi.features.competition.entity.CompetitionType;
import com.eagle.futbolapi.features.season.repository.SeasonRepository;

@Component
public class CompetitionMapper {

    private final SeasonRepository seasonRepository;

    public CompetitionMapper(SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }

    public CompetitionDTO toCompetitionDTO(Competition competition) {
        if (competition == null) {
            return null;
        }

        return CompetitionDTO.builder()
                .id(competition.getId())
                .seasonId(competition.getSeason() != null ? competition.getSeason().getId() : null)
                .name(competition.getName())
                .displayName(competition.getDisplayName())
                .type(competition.getType() != null ? competition.getType().name() : null)
                .startDate(competition.getStartDate())
                .endDate(competition.getEndDate())
                .active(competition.getActive())
                .totalMatchdays(competition.getTotalMatchdays())
                .description(competition.getDescription())
                .createdAt(competition.getCreatedAt())
                .createdBy(competition.getCreatedBy())
                .updatedAt(competition.getUpdatedAt())
                .updatedBy(competition.getUpdatedBy())
                .build();
    }

    public Competition toCompetition(CompetitionDTO dto) {
        if (dto == null) {
            return null;
        }

        var builder = Competition.builder()
                .id(dto.getId())
                .name(dto.getName())
                .displayName(dto.getDisplayName())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .active(dto.getActive())
                .totalMatchdays(dto.getTotalMatchdays())
                .description(dto.getDescription())
                .createdAt(dto.getCreatedAt())
                .createdBy(dto.getCreatedBy())
                .updatedAt(dto.getUpdatedAt())
                .updatedBy(dto.getUpdatedBy());

        if (dto.getType() != null) {
            try {
                builder.type(CompetitionType.valueOf(dto.getType().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid competition type: " + dto.getType());
            }
        }

        if (dto.getSeasonId() != null) {
            var season = seasonRepository.findById(dto.getSeasonId())
                    .orElseThrow(() -> new IllegalArgumentException("Season not found with id: " + dto.getSeasonId()));
            builder.season(season);
        }

        return builder.build();
    }
}
