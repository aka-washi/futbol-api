package com.eagle.futbolapi.features.stageFormat.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.enums.StageFormatType;
import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.stageFormat.dto.StageFormatDto;
import com.eagle.futbolapi.features.stageFormat.entity.StageFormat;

/**
 * MapStruct mapper for StageFormat entity and DTO conversion.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StageFormatMapper extends BaseMapper<StageFormat, StageFormatDto> {

  @Mapping(target = "pointSystemId", source = "pointSystem.id")
  @Mapping(target = "pointSystemDisplayName", source = "pointSystem.displayName")
  @Mapping(target = "type", expression = "java(typeToString(stageFormat.getType()))")
  StageFormatDto toDto(StageFormat stageFormat);

  @Mapping(target = "pointSystem", ignore = true)
  @Mapping(target = "type", expression = "java(stringToType(stageFormatDto.getType()))")
  StageFormat toEntity(StageFormatDto stageFormatDto);

  default String typeToString(StageFormatType type) {
    return type != null ? type.getLabel() : null;
  }

  default StageFormatType stringToType(String type) {
    if (type == null)
      return null;
    for (StageFormatType t : StageFormatType.values()) {
      if (t.getLabel().equals(type)) {
        return t;
      }
    }
    throw new IllegalArgumentException("Invalid stage format type: " + type);
  }
}
