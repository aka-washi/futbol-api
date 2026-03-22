package com.eagle.futbolapi.features.player.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.enums.PlayerPosition;
import com.eagle.futbolapi.features.base.enums.PreferredFoot;
import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.person.mapper.PersonMapper;
import com.eagle.futbolapi.features.player.dto.PlayerDto;
import com.eagle.futbolapi.features.player.entity.Player;

/**
 * MapStruct mapper for Player entity and DTO conversion.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = { PersonMapper.class })
public interface PlayerMapper extends BaseMapper<Player, PlayerDto> {

  @Mapping(target = "person", source = "person")
  @Mapping(target = "currentTeamId", source = "currentTeam.id")
  @Mapping(target = "currentTeamDisplayName", source = "currentTeam.displayName")
  @Mapping(target = "position", expression = "java(positionToString(player.getPosition()))")
  @Mapping(target = "preferredFoot", expression = "java(preferredFootToString(player.getPreferredFoot()))")
  PlayerDto toDto(Player player);

  @Mapping(target = "person", ignore = true)
  @Mapping(target = "currentTeam", ignore = true)
  @Mapping(target = "position", expression = "java(stringToPosition(playerDto.getPosition()))")
  @Mapping(target = "preferredFoot", expression = "java(stringToPreferredFoot(playerDto.getPreferredFoot()))")
  Player toEntity(PlayerDto playerDto);

  default String positionToString(PlayerPosition position) {
    return position != null ? position.getLabel() : null;
  }

  default PlayerPosition stringToPosition(String position) {
    return PlayerPosition.fromLabel(position);
  }

  default String preferredFootToString(PreferredFoot preferredFoot) {
    return preferredFoot != null ? preferredFoot.getLabel() : null;
  }

  default PreferredFoot stringToPreferredFoot(String preferredFoot) {
    return PreferredFoot.fromLabel(preferredFoot);
  }
}
