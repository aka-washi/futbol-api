package com.eagle.futbolapi.features.team.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.enums.AgeCategory;
import com.eagle.futbolapi.features.base.enums.Gender;
import com.eagle.futbolapi.features.base.enums.TeamStatus;
import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.team.dto.TeamDto;
import com.eagle.futbolapi.features.team.entity.Team;

/**
 * MapStruct mapper for Team entity and DTO conversion.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TeamMapper extends BaseMapper<Team, TeamDto> {


  @Mapping(target = "organizationId", source = "organization.id")
  @Mapping(target = "organizationDisplayName", source = "organization.displayName")
  @Mapping(target = "countryId", source = "country.id")
  @Mapping(target = "countryDisplayName", source = "country.displayName")
  @Mapping(target = "venueId", source = "venue.id")
  @Mapping(target = "venueDisplayName", source = "venue.displayName")
  @Mapping(target = "gender", expression = "java(genderToString(team.getGender()))")
  @Mapping(target = "ageCategory", expression = "java(ageCategoryToString(team.getAgeCategory()))")
  @Mapping(target = "status", expression = "java(teamStatusToString(team.getStatus()))")
  TeamDto toDto(Team team);


  @Mapping(target = "organization", ignore = true)
  @Mapping(target = "country", ignore = true)
  @Mapping(target = "venue", ignore = true)
  @Mapping(target = "gender", expression = "java(stringToGender(teamDto.getGender()))")
  @Mapping(target = "ageCategory", expression = "java(stringToAgeCategory(teamDto.getAgeCategory()))")
  @Mapping(target = "status", expression = "java(stringToTeamStatus(teamDto.getStatus()))")
  Team toEntity(TeamDto teamDto);

  default String genderToString(Gender gender) {
    return gender != null ? gender.getLabel() : null;
  }

  default Gender stringToGender(String gender) {
    return Gender.fromLabel(gender);
  }

  default String ageCategoryToString(AgeCategory ageCategory) {
    return ageCategory != null ? ageCategory.getLabel() : null;
  }

  default AgeCategory stringToAgeCategory(String ageCategory) {
    return AgeCategory.fromLabel(ageCategory);
  }

  default String teamStatusToString(TeamStatus status) {
    return status != null ? status.getLabel() : null;
  }

  default TeamStatus stringToTeamStatus(String status) {
    return TeamStatus.fromLabel(status);
  }

}
