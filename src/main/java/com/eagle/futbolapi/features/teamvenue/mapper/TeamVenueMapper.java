package com.eagle.futbolapi.features.teamvenue.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.teamvenue.dto.TeamVenueDto;
import com.eagle.futbolapi.features.teamvenue.entity.TeamVenue;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TeamVenueMapper extends BaseMapper<TeamVenue, TeamVenueDto> {

  @Mapping(target = "teamId", source = "team.id")
  @Mapping(target = "teamDisplayName", source = "team.displayName")
  @Mapping(target = "venueId", source = "venue.id")
  @Mapping(target = "venueDisplayName", source = "venue.displayName")
  TeamVenueDto toDto(TeamVenue entity);

  @Mapping(target = "team", ignore = true)
  @Mapping(target = "venue", ignore = true)
  TeamVenue toEntity(TeamVenueDto dto);

}
