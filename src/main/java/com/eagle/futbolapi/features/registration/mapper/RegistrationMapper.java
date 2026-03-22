package com.eagle.futbolapi.features.registration.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.registration.dto.RegistrationDto;
import com.eagle.futbolapi.features.registration.entity.Registration;

/**
 * MapStruct mapper for Registration entity and DTO conversion.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RegistrationMapper extends BaseMapper<Registration, RegistrationDto> {

  @Mapping(target = "competition", ignore = true)
  @Mapping(target = "team", ignore = true)
  @Mapping(target = "player", ignore = true)
  @Mapping(target = "staff", ignore = true)
  Registration toEntity(RegistrationDto dto);

  @Mapping(source = "competition.id", target = "competitionId")
  @Mapping(source = "competition.displayName", target = "competitionDisplayName")
  @Mapping(source = "team.id", target = "teamId")
  @Mapping(source = "team.displayName", target = "teamDisplayName")
  @Mapping(source = "player.id", target = "playerId")
  @Mapping(source = "player.person.displayName", target = "playerDisplayName")
  @Mapping(source = "staff.id", target = "staffId")
  @Mapping(source = "staff.person.displayName", target = "staffDisplayName")
  RegistrationDto toDto(Registration registration);

}
