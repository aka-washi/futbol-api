package com.eagle.futbolapi.features.seasonTeam.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.seasonTeam.dto.SeasonTeamDto;
import com.eagle.futbolapi.features.seasonTeam.entity.SeasonTeam;

/**
 * MapStruct mapper for SeasonTeam entity and DTO conversion.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SeasonTeamMapper extends BaseMapper<SeasonTeam, SeasonTeamDto> {

  @Mapping(target = "seasonId", source = "season.id")
  @Mapping(target = "seasonDisplayName", source = "season.displayName")
  @Mapping(target = "teamId", source = "team.id")
  @Mapping(target = "teamDisplayName", source = "team.displayName")
  SeasonTeamDto toDto(SeasonTeam seasonTeam);

  @Mapping(target = "season", ignore = true)
  @Mapping(target = "team", ignore = true)
  SeasonTeam toEntity(SeasonTeamDto seasonTeamDto);
}
