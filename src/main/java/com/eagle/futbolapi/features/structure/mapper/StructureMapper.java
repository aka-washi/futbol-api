package com.eagle.futbolapi.features.structure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.structure.dto.StructureDTO;
import com.eagle.futbolapi.features.structure.entity.Structure;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StructureMapper {

  @Mapping(target = "pointSystemId", source = "pointSystem.id")
  @Mapping(target = "pointSystemDisplayName", source = "pointSystem.displayName")
  StructureDTO toStructureDTO(Structure structure);

  @Mapping(target = "pointSystem", ignore = true)
  Structure toStructure(StructureDTO dto);

}
