package com.eagle.futbolapi.features.competitionoutcome.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.enums.OutcomeType;
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
  @Mapping(target = "teamId", source = "team.id")
  @Mapping(target = "teamDisplayName", source = "team.displayName")
  @Mapping(target = "outcomeType", expression = "java(outcomeTypeToString(competitionOutcome.getOutcomeType()))")
  CompetitionOutcomeDto toDto(CompetitionOutcome competitionOutcome);

  @Mapping(target = "competition", ignore = true)
  @Mapping(target = "team", ignore = true)
  @Mapping(target = "outcomeType", expression = "java(stringToOutcomeType(competitionOutcomeDto.getOutcomeType()))")
  CompetitionOutcome toEntity(CompetitionOutcomeDto competitionOutcomeDto);

  default String outcomeTypeToString(OutcomeType outcomeType) {
    return outcomeType != null ? outcomeType.getLabel() : null;
  }

  default OutcomeType stringToOutcomeType(String outcomeTypeLabel) {
    return outcomeTypeLabel != null ? OutcomeType.fromLabel(outcomeTypeLabel) : null;
  }

}
