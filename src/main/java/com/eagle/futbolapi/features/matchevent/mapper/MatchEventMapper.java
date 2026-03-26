package com.eagle.futbolapi.features.matchevent.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.enums.MatchEventType;
import com.eagle.futbolapi.features.base.enums.MatchPeriod;
import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.matchevent.dto.MatchEventDto;
import com.eagle.futbolapi.features.matchevent.entity.MatchEvent;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MatchEventMapper extends BaseMapper<MatchEvent, MatchEventDto> {

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
  @Mapping(target = "type", expression = "java(matchEventTypeToString(matchEvent.getType()))")
  @Mapping(target = "period", expression = "java(matchPeriodToString(matchEvent.getPeriod()))")
  MatchEventDto toDto(MatchEvent matchEvent);

  @Mapping(target = "match", ignore = true)
  @Mapping(target = "team", ignore = true)
  @Mapping(target = "player", ignore = true)
  @Mapping(target = "assistPlayer", ignore = true)
  @Mapping(target = "substitutePlayer", ignore = true)
  @Mapping(target = "type", expression = "java(stringToMatchEventType(matchEventDto.getType()))")
  @Mapping(target = "period", expression = "java(stringToMatchPeriod(matchEventDto.getPeriod()))")
  MatchEvent toEntity(MatchEventDto matchEventDto);

  default String matchEventTypeToString(MatchEventType type) {
    return type != null ? type.getLabel() : null;
  }

  default MatchEventType stringToMatchEventType(String type) {
    if (type == null)
      return null;
    for (MatchEventType t : MatchEventType.values()) {
      if (t.getLabel().equalsIgnoreCase(type) || t.name().equalsIgnoreCase(type)) {
        return t;
      }
    }
    throw new IllegalArgumentException("Invalid match event type: " + type);
  }

  default String matchPeriodToString(MatchPeriod period) {
    return period != null ? period.getLabel() : null;
  }

  default MatchPeriod stringToMatchPeriod(String period) {
    if (period == null)
      return null;
    for (MatchPeriod p : MatchPeriod.values()) {
      if (p.getLabel().equalsIgnoreCase(period) || p.name().equalsIgnoreCase(period)) {
        return p;
      }
    }
    throw new IllegalArgumentException("Invalid match period: " + period);
  }

}
