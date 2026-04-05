package com.eagle.futbolapi.features.qualificationoutcome.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.qualificationoutcome.dto.QualificationOutcomeDto;
import com.eagle.futbolapi.features.qualificationoutcome.entity.QualificationOutcome;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface QualificationOutcomeMapper extends BaseMapper<QualificationOutcome, QualificationOutcomeDto> {

  @Mapping(target = "sourceCompetitionId", source = "sourceCompetition.id")
  @Mapping(target = "sourceCompetitionDisplayName", source = "sourceCompetition.displayName")
  @Mapping(target = "targetCompetitionId", source = "targetCompetition.id")
  @Mapping(target = "targetCompetitionDisplayName", source = "targetCompetition.displayName")
  @Mapping(target = "teamId", source = "team.id")
  @Mapping(target = "teamDisplayName", source = "team.displayName")
  QualificationOutcomeDto toDto(QualificationOutcome entity);

  @Mapping(target = "sourceCompetition", ignore = true)
  @Mapping(target = "targetCompetition", ignore = true)
  @Mapping(target = "team", ignore = true)
  QualificationOutcome toEntity(QualificationOutcomeDto dto);

}
