package com.eagle.futbolapi.features.staff.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.staff.dto.StaffDTO;
import com.eagle.futbolapi.features.staff.entity.Staff;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StaffMapper extends BaseMapper<Staff, StaffDTO> {

  @Mapping(target = "personId", source = "person.id")
  @Mapping(target = "personDisplayName", source = "person.displayName")
  @Mapping(target = "currentTeamId", source = "currentTeam.id")
  @Mapping(target = "currentTeamDisplayName", source = "currentTeam.displayName")
  StaffDTO toDTO(Staff staff);

  @Mapping(target = "person", ignore = true)
  @Mapping(target = "currentTeam", ignore = true)
  Staff toEntity(StaffDTO staffDTO);

}
