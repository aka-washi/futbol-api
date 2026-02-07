package com.eagle.futbolapi.features.team.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.team.dto.TeamDTO;
import com.eagle.futbolapi.features.team.entity.Team;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TeamMapper extends BaseMapper<Team, TeamDTO> {

  @Mapping(target = "countryId", source = "country.id")
  @Mapping(target = "countryDisplayName", source = "country.displayName")
  @Mapping(target = "organizationId", source = "organization.id")
  @Mapping(target = "organizationDisplayName", source = "organization.displayName")
  @Mapping(target = "venueId", source = "venue.id")
  @Mapping(target = "venueDisplayName", source = "venue.displayName")
  TeamDTO toDTO(Team team);

  @Mapping(target = "country", ignore = true)
  @Mapping(target = "organization", ignore = true)
  @Mapping(target = "venue", ignore = true)
  Team toEntity(TeamDTO teamDTO);
}
