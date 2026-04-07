package com.eagle.futbolapi.features.seasonParticipation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.seasonParticipation.dto.SeasonParticipationDto;
import com.eagle.futbolapi.features.seasonParticipation.entity.SeasonParticipation;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SeasonParticipationMapper extends BaseMapper<SeasonParticipation, SeasonParticipationDto> {

  @Mapping(target = "seasonId", source = "season.id")
  @Mapping(target = "seasonDisplayName", source = "season.displayName")
  @Mapping(target = "teamId", source = "team.id")
  @Mapping(target = "teamDisplayName", source = "team.displayName")
  SeasonParticipationDto toDto(SeasonParticipation entity);

  @Mapping(target = "season", ignore = true)
  @Mapping(target = "team", ignore = true)
  SeasonParticipation toEntity(SeasonParticipationDto dto);

}
