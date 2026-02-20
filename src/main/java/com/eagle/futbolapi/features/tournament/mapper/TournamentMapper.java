package com.eagle.futbolapi.features.tournament.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.enums.AgeCategory;
import com.eagle.futbolapi.features.base.enums.TournamentType;
import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.tournament.dto.TournamentDto;
import com.eagle.futbolapi.features.tournament.entity.Tournament;
/**
 * MapStruct mapper for converting between Tournament entities and TournamentDto
 * objects.
 * Provides bidirectional mapping functionality with automatic field mapping.
 *
 * <p>
 * This mapper is automatically implemented by MapStruct at compile time
 * and registered as a Spring bean through the component model configuration.
 *
 * @see Tournament
 * @see TournamentDto
 * @see BaseMapper
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TournamentMapper extends BaseMapper<Tournament, TournamentDto> {

  @Mapping(target = "organizationId", source = "organization.id")
  @Mapping(target = "organizationDisplayName", source = "organization.displayName")
  @Mapping(target = "relegationToId", source = "relegationTo.id")
  @Mapping(target = "relegationToDisplayName", source = "relegationTo.displayName")
  @Mapping(target = "type", expression = "java(tournamentTypeToString(tournament.getType()))")
  @Mapping(target = "ageCategory", expression = "java(ageCategoryToString(tournament.getAgeCategory()))")
  TournamentDto toDto(Tournament tournament);

  @Mapping(target = "organization", ignore = true) // Handle organization mapping separately
  @Mapping(target = "relegationTo", ignore = true) // Handle relegationTo mapping separately
  @Mapping(target = "type", expression = "java(stringToTournamentType(tournamentDto.getType()))")
  @Mapping(target = "ageCategory", expression = "java(stringToAgeCategory(tournamentDto.getAgeCategory()))")
  Tournament toEntity(TournamentDto tournamentDto);


  default String tournamentTypeToString(TournamentType tournamentType) {
    return tournamentType != null ? tournamentType.getLabel() : null;
  }

  default TournamentType stringToTournamentType(String tournamentType) {
    if (tournamentType == null)
      return null;
    for (TournamentType type : TournamentType.values()) {
      if (type.getLabel().equals(tournamentType)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Invalid tournament type: " + tournamentType);
  }

  default String ageCategoryToString(AgeCategory ageCategory) {
    return ageCategory != null ? ageCategory.getLabel() : null;
  }

  default AgeCategory stringToAgeCategory(String ageCategory) {
    if (ageCategory == null)
      return null;
    for (AgeCategory type : AgeCategory.values()) {
      if (type.getLabel().equals(ageCategory)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Invalid age category: " + ageCategory);
  }
}
