package com.eagle.futbolapi.features.competition.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.enums.CompetitionStatus;
import com.eagle.futbolapi.features.base.enums.CompetitionType;
import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.competition.dto.CompetitionDto;
import com.eagle.futbolapi.features.competition.entity.Competition;

/**
 * MapStruct mapper for Competition entity and DTO conversion.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompetitionMapper extends BaseMapper<Competition, CompetitionDto> {

  @Mapping(target = "tournamentSeasonId", source = "tournamentSeason.id")
  @Mapping(target = "tournamentSeasonDisplayName", source = "tournamentSeason.displayName")
  @Mapping(target = "type", expression = "java(typeToString(competition.getType()))")
  @Mapping(target = "status", expression = "java(statusToString(competition.getStatus()))")
  CompetitionDto toDto(Competition competition);

  @Mapping(target = "tournamentSeason", ignore = true)
  @Mapping(target = "type", expression = "java(stringToType(competitionDto.getType()))")
  @Mapping(target = "status", expression = "java(stringToStatus(competitionDto.getStatus()))")
  Competition toEntity(CompetitionDto competitionDto);

  default String typeToString(CompetitionType type) {
    return type != null ? type.getLabel() : null;
  }

  default CompetitionType stringToType(String type) {
    if (type == null)
      return null;
    for (CompetitionType t : CompetitionType.values()) {
      if (t.getLabel().equals(type)) {
        return t;
      }
    }
    throw new IllegalArgumentException("Invalid competition type: " + type);
  }

  default String statusToString(CompetitionStatus status) {
    return status != null ? status.getLabel() : null;
  }

  default CompetitionStatus stringToStatus(String status) {
    if (status == null)
      return null;
    for (CompetitionStatus s : CompetitionStatus.values()) {
      if (s.getLabel().equals(status)) {
        return s;
      }
    }
    throw new IllegalArgumentException("Invalid competition status: " + status);
  }
}
