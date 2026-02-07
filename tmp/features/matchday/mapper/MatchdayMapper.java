package com.eagle.futbolapi.features.matchday.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.matchday.dto.MatchdayDTO;
import com.eagle.futbolapi.features.matchday.entity.Matchday;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MatchdayMapper extends BaseMapper<Matchday, MatchdayDTO> {

  @Override
  @Mapping(target = "stageId", source = "stage.id")
  @Mapping(target = "stageDisplayName", source = "stage.displayName")
  MatchdayDTO toDTO(Matchday matchday);

  @Override
  @Mapping(target = "stage", ignore = true)
  Matchday toEntity(MatchdayDTO matchdayDTO);
}
