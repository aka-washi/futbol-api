package com.eagle.futbolapi.features.player.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.player.dto.PlayerDTO;
import com.eagle.futbolapi.features.player.entity.Player;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlayerMapper extends BaseMapper<Player, PlayerDTO> {

  @Mapping(target = "personId", source = "person.id")
  @Mapping(target = "personDisplayName", source = "person.displayName")
  @Mapping(target = "currentTeamId", source = "currentTeam.id")
  @Mapping(target = "currentTeamDisplayName", source = "currentTeam.displayName")
  PlayerDTO toDTO(Player entity);

  @Mapping(target = "person", ignore = true)
  @Mapping(target = "currentTeam", ignore = true)
  Player toEntity(PlayerDTO dto);

}
