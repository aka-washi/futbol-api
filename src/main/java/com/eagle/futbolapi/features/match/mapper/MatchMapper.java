package com.eagle.futbolapi.features.match.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.enums.MatchStatus;
import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.match.dto.MatchDto;
import com.eagle.futbolapi.features.match.entity.Match;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MatchMapper extends BaseMapper<Match, MatchDto> {

  @Mapping(target = "matchdayId", source = "matchday.id")
  @Mapping(target = "matchdayDisplayName", source = "matchday.displayName")
  @Mapping(target = "homeTeamId", source = "homeTeam.id")
  @Mapping(target = "homeTeamDisplayName", source = "homeTeam.displayName")
  @Mapping(target = "awayTeamId", source = "awayTeam.id")
  @Mapping(target = "awayTeamDisplayName", source = "awayTeam.displayName")
  @Mapping(target = "venueId", source = "venue.id")
  @Mapping(target = "venueDisplayName", source = "venue.displayName")
  @Mapping(target = "refereeId", source = "referee.id")
  @Mapping(target = "refereeDisplayName", source = "referee.displayName")
  @Mapping(target = "status", expression = "java(matchStatusToString(match.getStatus()))")
  MatchDto toDto(Match match);

  @Mapping(target = "matchday", ignore = true)
  @Mapping(target = "homeTeam", ignore = true)
  @Mapping(target = "awayTeam", ignore = true)
  @Mapping(target = "venue", ignore = true)
  @Mapping(target = "referee", ignore = true)
  @Mapping(target = "status", expression = "java(stringToMatchStatus(matchDto.getStatus()))")
  Match toEntity(MatchDto matchDto);

  default String matchStatusToString(MatchStatus status) {
    return status != null ? status.getLabel() : null;
  }

  default MatchStatus stringToMatchStatus(String status) {
    return MatchStatus.fromLabel(status);
  }
}
