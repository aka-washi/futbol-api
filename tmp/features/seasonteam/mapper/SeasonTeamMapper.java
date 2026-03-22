package com.eagle.futbolapi.features.seasonteam.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.seasonteam.dto.SeasonTeamDTO;
import com.eagle.futbolapi.features.seasonteam.entity.SeasonTeam;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SeasonTeamMapper extends BaseMapper<SeasonTeam, SeasonTeamDTO> {

  @Mapping(target = "seasonId", source = "season.id")
  @Mapping(target = "seasonDisplayName", source = "season.displayName")
  @Mapping(target = "teamId", source = "team.id")
  @Mapping(target = "teamDisplayName", source = "team.displayName")
  SeasonTeamDTO toDTO(SeasonTeam seasonTeam);

  @Mapping(target = "season", ignore = true)
  @Mapping(target = "team", ignore = true)
  SeasonTeam toEntity(SeasonTeamDTO seasonTeamDTO);

}
