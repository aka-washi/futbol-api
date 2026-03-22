package com.eagle.futbolapi.features.group.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.group.dto.GroupDto;
import com.eagle.futbolapi.features.group.entity.Group;

/**
 * MapStruct mapper for Group entity and DTO conversion.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GroupMapper extends BaseMapper<Group, GroupDto> {

  @Mapping(target = "stageId", source = "stage.id")
  @Mapping(target = "stageDisplayName", source = "stage.displayName")
  GroupDto toDto(Group group);

  @Mapping(target = "stage", ignore = true)
  Group toEntity(GroupDto groupDto);
}
