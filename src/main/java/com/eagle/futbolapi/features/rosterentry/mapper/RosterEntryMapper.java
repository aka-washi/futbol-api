package com.eagle.futbolapi.features.rosterentry.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.rosterentry.dto.RosterEntryDTO;
import com.eagle.futbolapi.features.rosterentry.entity.RosterEntry;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RosterEntryMapper extends BaseMapper<RosterEntry, RosterEntryDTO> {

  @Mapping(target = "seasonId", source = "season.id")
  @Mapping(target = "seasonDisplayName", source = "season.displayName")
  @Mapping(target = "teamId", source = "team.id")
  @Mapping(target = "teamDisplayName", source = "team.displayName")
  @Mapping(target = "playerId", source = "player.id")
  @Mapping(target = "playerDisplayName", source = "player.displayName")
  @Mapping(target = "staffId", source = "staff.id")
  @Mapping(target = "staffDisplayName", source = "staff.displayName")
  RosterEntryDTO toDTO(RosterEntry rosterEntry);

  @Mapping(target = "season", ignore = true)
  @Mapping(target = "team", ignore = true)
  @Mapping(target = "player", ignore = true)
  @Mapping(target = "staff", ignore = true)
  RosterEntry toEntity(RosterEntryDTO rosterEntryDTO);

}
