package com.eagle.futbolapi.features.staff.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.enums.StaffRole;
import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.staff.dto.StaffDto;
import com.eagle.futbolapi.features.staff.entity.Staff;

/**
 * MapStruct mapper for Staff entity and DTO conversion.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StaffMapper extends BaseMapper<Staff, StaffDto> {

  @Mapping(target = "personId", source = "person.id")
  @Mapping(target = "personDisplayName", source = "person.displayName")
  @Mapping(target = "currentTeamId", source = "currentTeam.id")
  @Mapping(target = "currentTeamDisplayName", source = "currentTeam.displayName")
  @Mapping(target = "role", expression = "java(roleToString(staff.getRole()))")
  StaffDto toDto(Staff staff);

  @Mapping(target = "person", ignore = true)
  @Mapping(target = "currentTeam", ignore = true)
  @Mapping(target = "role", expression = "java(stringToRole(staffDto.getRole()))")
  Staff toEntity(StaffDto staffDto);

  default String roleToString(StaffRole role) {
    return role != null ? role.getLabel() : null;
  }

  default StaffRole stringToRole(String role) {
    if (role == null)
      return null;
    for (StaffRole sr : StaffRole.values()) {
      if (sr.getLabel().equals(role)) {
        return sr;
      }
    }
    throw new IllegalArgumentException("Invalid staff role: " + role);
  }
}
