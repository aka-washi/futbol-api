package com.eagle.futbolapi.features.lineupMember.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.enums.LineupMemberRoleType;
import com.eagle.futbolapi.features.base.enums.PersonType;
import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.lineupMember.dto.LineupMemberDto;
import com.eagle.futbolapi.features.lineupMember.entity.LineupMember;

/**
 * MapStruct mapper for LineupMember entity and DTO conversion.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LineupMemberMapper extends BaseMapper<LineupMember, LineupMemberDto> {

  @Mapping(target = "lineupId", source = "lineup.id")
  @Mapping(target = "lineupDisplayName", source = "lineup.displayName")
  @Mapping(target = "playerId", source = "player.id")
  @Mapping(target = "playerDisplayName", source = "player.person.displayName")
  @Mapping(target = "staffId", source = "staff.id")
  @Mapping(target = "staffDisplayName", source = "staff.person.displayName")
  @Mapping(target = "personType", expression = "java(personTypeToString(lineupMember.getPersonType()))")
  @Mapping(target = "roleType", expression = "java(roleTypeToString(lineupMember.getRoleType()))")
  LineupMemberDto toDto(LineupMember lineupMember);

  @Mapping(target = "lineup", ignore = true)
  @Mapping(target = "player", ignore = true)
  @Mapping(target = "staff", ignore = true)
  @Mapping(target = "personType", expression = "java(stringToPersonType(lineupMemberDto.getPersonType()))")
  @Mapping(target = "roleType", expression = "java(stringToRoleType(lineupMemberDto.getRoleType()))")
  LineupMember toEntity(LineupMemberDto lineupMemberDto);

  default String personTypeToString(PersonType personType) {
    return personType != null ? personType.getLabel() : null;
  }

  default PersonType stringToPersonType(String personType) {
    if (personType == null)
      return null;
    for (PersonType pt : PersonType.values()) {
      if (pt.getLabel().equals(personType)) {
        return pt;
      }
    }
    throw new IllegalArgumentException("Invalid person type: " + personType);
  }

  default String roleTypeToString(LineupMemberRoleType roleType) {
    return roleType != null ? roleType.getLabel() : null;
  }

  default LineupMemberRoleType stringToRoleType(String roleType) {
    if (roleType == null)
      return null;
    for (LineupMemberRoleType rt : LineupMemberRoleType.values()) {
      if (rt.getLabel().equals(roleType)) {
        return rt;
      }
    }
    throw new IllegalArgumentException("Invalid role type: " + roleType);
  }
}
