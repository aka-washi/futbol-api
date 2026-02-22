package com.eagle.futbolapi.features.tournamentSeason.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.enums.CompetitionStatus;
import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.tournamentSeason.dto.TournamentSeasonDto;
import com.eagle.futbolapi.features.tournamentSeason.entity.TournamentSeason;

/**
 * MapStruct mapper for TournamentSeason entity and DTO conversion.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TournamentSeasonMapper extends BaseMapper<TournamentSeason, TournamentSeasonDto> {

  @Mapping(target = "seasonId", source = "season.id")
  @Mapping(target = "seasonDisplayName", source = "season.displayName")
  @Mapping(target = "tournamentId", source = "tournament.id")
  @Mapping(target = "tournamentDisplayName", source = "tournament.displayName")
  @Mapping(target = "status", expression = "java(statusToString(tournamentSeason.getStatus()))")
  TournamentSeasonDto toDto(TournamentSeason tournamentSeason);

  @Mapping(target = "season", ignore = true)
  @Mapping(target = "tournament", ignore = true)
  @Mapping(target = "status", expression = "java(stringToStatus(tournamentSeasonDto.getStatus()))")
  TournamentSeason toEntity(TournamentSeasonDto tournamentSeasonDto);

  default String statusToString(CompetitionStatus status) {
    return status != null ? status.getLabel() : null;
  }

  default CompetitionStatus stringToStatus(String status) {
    if (status == null)
      return null;
    for (CompetitionStatus s : CompetitionStatus.values()) {
      if (s.getLabel().equals(status)) {
        return s;
      }
    }
    throw new IllegalArgumentException("Invalid status: " + status);
  }
}
