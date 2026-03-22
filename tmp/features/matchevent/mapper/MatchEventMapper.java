package com.eagle.futbolapi.features.matchevent.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.matchevent.dto.MatchEventDTO;
import com.eagle.futbolapi.features.matchevent.entity.MatchEvent;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MatchEventMapper extends BaseMapper<MatchEvent, MatchEventDTO> {

  @Mapping(target = "matchId", source = "match.id")
  @Mapping(target = "matchDisplayName", source = "match.displayName")
  @Mapping(target = "teamId", source = "team.id")
  @Mapping(target = "teamDisplayName", source = "team.displayName")
  @Mapping(target = "playerId", source = "player.id")
  @Mapping(target = "playerDisplayName", source = "player.person.displayName")
  @Mapping(target = "assistPlayerId", source = "assistPlayer.id")
  @Mapping(target = "assistPlayerDisplayName", source = "assistPlayer.person.displayName")
  @Mapping(target = "substitutePlayerId", source = "substitutePlayer.id")
  @Mapping(target = "substitutePlayerDisplayName", source = "substitutePlayer.person.displayName")
  MatchEventDTO toDTO(MatchEvent matchEvent);

  @Mapping(target = "match", ignore = true)
  @Mapping(target = "team", ignore = true)
  @Mapping(target = "player", ignore = true)
  @Mapping(target = "assistPlayer", ignore = true)
  @Mapping(target = "substitutePlayer", ignore = true)
  MatchEvent toEntity(MatchEventDTO matchEventDTO);

}
