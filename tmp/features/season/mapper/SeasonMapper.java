package com.eagle.futbolapi.features.season.mapper;

import org.springframework.stereotype.Component;

import com.eagle.futbolapi.features.season.dto.SeasonDTO;
import com.eagle.futbolapi.features.season.entity.Season;
import com.eagle.futbolapi.features.tournament.repository.TournamentRepository;

@Component
public class SeasonMapper {

    private final TournamentRepository tournamentRepository;

    public SeasonMapper(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public SeasonDTO toSeasonDTO(Season season) {
        if (season == null) {
            return null;
        }

        return SeasonDTO.builder()
                .id(season.getId())
                .tournamentId(season.getTournament() != null ? season.getTournament().getId() : null)
                .tournamentDisplayName(season.getTournament() != null ? season.getTournament().getDisplayName() : null)
                .name(season.getName())
                .displayName(season.getDisplayName())
                .startDate(season.getStartDate())
                .endDate(season.getEndDate())
                .active(season.getActive())
                .hasRelegation(season.getHasRelegation())
                .description(season.getDescription())
                .createdAt(season.getCreatedAt())
                .createdBy(season.getCreatedBy())
                .updatedAt(season.getUpdatedAt())
                .updatedBy(season.getUpdatedBy())
                .build();
    }

    public Season toSeason(SeasonDTO dto) {
        if (dto == null) {
            return null;
        }

        var builder = Season.builder()
                .id(dto.getId())
                .name(dto.getName())
                .displayName(dto.getDisplayName())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .active(dto.getActive())
                .hasRelegation(dto.getHasRelegation())
                .description(dto.getDescription())
                .createdAt(dto.getCreatedAt())
                .createdBy(dto.getCreatedBy())
                .updatedAt(dto.getUpdatedAt())
                .updatedBy(dto.getUpdatedBy());

        if (dto.getTournamentId() != null) {
            var tournament = tournamentRepository.findById(dto.getTournamentId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Tournament not found with id: " + dto.getTournamentId()));
            builder.tournament(tournament);
        }

        return builder.build();
    }
}
