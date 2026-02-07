package com.eagle.futbolapi.features.tournament.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.tournament.dto.TournamentDTO;
import com.eagle.futbolapi.features.tournament.entity.Tournament;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TournamentMapper extends BaseMapper<Tournament, TournamentDTO> {

  @Mapping(target = "organizationId", source = "organization.id")
  @Mapping(target = "organizationDisplayName", source = "organization.displayName")
  @Mapping(target = "relegationToId", source = "tournament.id")
  @Mapping(target = "relegationToDisplayName", source = "tournament.displayName")
  TournamentDTO toDTO(Tournament tournament);

  @Mapping(target = "organization", ignore = true)
  @Mapping(target = "relegationTo", ignore = true)
  Tournament toEntity(TournamentDTO tournamentDTO);

}
