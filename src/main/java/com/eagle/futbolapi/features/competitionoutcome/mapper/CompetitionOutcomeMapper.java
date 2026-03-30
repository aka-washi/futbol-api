package com.eagle.futbolapi.features.competitionoutcome.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.competitionoutcome.dto.CompetitionOutcomeDto;
import com.eagle.futbolapi.features.competitionoutcome.entity.CompetitionOutcome;

/**
 * MapStruct mapper for CompetitionOutcome entity and DTO conversion.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompetitionOutcomeMapper extends BaseMapper<CompetitionOutcome, CompetitionOutcomeDto> {

  @Mapping(target = "competitionId", source = "competition.id")
  @Mapping(target = "competitionDisplayName", source = "competition.displayName")
  CompetitionOutcomeDto toDto(CompetitionOutcome competitionOutcome);

  @Mapping(target = "competition", ignore = true)
  CompetitionOutcome toEntity(CompetitionOutcomeDto competitionOutcomeDto);

}
