package com.eagle.futbolapi.features.competition.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.competition.dto.CompetitionDTO;
import com.eagle.futbolapi.features.competition.entity.Competition;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompetitionMapper extends BaseMapper<Competition, CompetitionDTO> {

  @Mapping(target = "seasonId", source = "season.id")
  @Mapping(target = "seasonDisplayName", source = "season.displayName")
  CompetitionDTO toDTO(Competition competition);

  @Mapping(target = "season", ignore = true)
  Competition toEntity(CompetitionDTO competitionDTO);
}
