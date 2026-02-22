package com.eagle.futbolapi.features.standing.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.standing.dto.StandingDto;
import com.eagle.futbolapi.features.standing.entity.Standing;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StandingMapper extends BaseMapper<Standing, StandingDto> {

  @Mapping(target = "stageId", source = "stage.id")
  @Mapping(target = "stageDisplayName", source = "stage.displayName")
  @Mapping(target = "teamId", source = "team.id")
  @Mapping(target = "teamDisplayName", source = "team.displayName")
  StandingDto toDto(Standing standing);

  @Mapping(target = "stage", ignore = true)
  @Mapping(target = "team", ignore = true)
  Standing toEntity(StandingDto standingDto);

}
