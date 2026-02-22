package com.eagle.futbolapi.features.matchday.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.matchday.dto.MatchdayDto;
import com.eagle.futbolapi.features.matchday.entity.Matchday;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MatchdayMapper extends BaseMapper<Matchday, MatchdayDto> {

  @Override
  @Mapping(target = "stageId", source = "stage.id")
  @Mapping(target = "stageDisplayName", source = "stage.displayName")
  MatchdayDto toDto(Matchday matchday);

  @Override
  @Mapping(target = "stage", ignore = true)
  Matchday toEntity(MatchdayDto matchdayDto);
}
