package com.eagle.futbolapi.features.lineup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.lineup.dto.LineupDto;
import com.eagle.futbolapi.features.lineup.entity.Lineup;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LineupMapper extends BaseMapper<Lineup, LineupDto> {

  @Mapping(target = "matchId", source = "match.id")
  @Mapping(target = "matchDisplayName", source = "match.displayName")
  @Mapping(target = "teamId", source = "team.id")
  @Mapping(target = "teamDisplayName", source = "team.displayName")
  LineupDto toDto(Lineup entity);

  @Mapping(target = "match", ignore = true)
  @Mapping(target = "team", ignore = true)
  Lineup toEntity(LineupDto dto);

}
