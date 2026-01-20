package com.eagle.futbolapi.features.season.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.season.dto.SeasonDTO;
import com.eagle.futbolapi.features.season.entity.Season;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SeasonMapper {

  @Mapping(target = "tournamentId", source = "tournament.id")
  @Mapping(target = "tournamentDisplayName", source = "tournament.displayName")
  SeasonDTO toSeasonDTO(Season season);

  @Mapping(target = "tournament", ignore = true)
  Season toSeason(SeasonDTO seasonDTO);

}
