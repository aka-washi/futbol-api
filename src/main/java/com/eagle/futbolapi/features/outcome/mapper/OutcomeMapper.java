package com.eagle.futbolapi.features.outcome.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.outcome.dto.OutcomeDTO;
import com.eagle.futbolapi.features.outcome.entity.Outcome;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OutcomeMapper extends BaseMapper<Outcome, OutcomeDTO> {

  @Mapping(target = "competitionId", source = "competition.id")
  @Mapping(target = "competitionDisplayName", source = "competition.displayName")
  @Mapping(target = "teamId", source = "team.id")
  @Mapping(target = "teamDisplayName", source = "team.displayName")
  @Mapping(target = "playerId", source = "player.id")
  @Mapping(target = "playerPersonDisplayName", source = "player.person.displayName")
  @Mapping(target = "staffId", source = "staff.id")
  @Mapping(target = "staffPersonDisplayName", source = "staff.person.displayName")
  OutcomeDTO toDTO(Outcome entity);

  @Mapping(target = "competition", ignore = true)
  @Mapping(target = "team", ignore = true)
  @Mapping(target = "player", ignore = true)
  @Mapping(target = "staff", ignore = true)
  Outcome toEntity(OutcomeDTO dto);

}
