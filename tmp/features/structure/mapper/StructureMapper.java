package com.eagle.futbolapi.features.structure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.structure.dto.StructureDTO;
import com.eagle.futbolapi.features.structure.entity.Structure;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StructureMapper extends BaseMapper<Structure, StructureDTO> {

  @Mapping(target = "pointSystemId", source = "pointSystem.id")
  @Mapping(target = "pointSystemDisplayName", source = "pointSystem.displayName")
  StructureDTO toDTO(Structure structure);

  @Mapping(target = "pointSystem", ignore = true)
  Structure toEntity(StructureDTO dto);

}
