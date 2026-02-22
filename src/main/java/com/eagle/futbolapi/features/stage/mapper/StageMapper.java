package com.eagle.futbolapi.features.stage.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.enums.StageStatus;
import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.stage.dto.StageDto;
import com.eagle.futbolapi.features.stage.entity.Stage;

/**
 * MapStruct mapper for Stage entity and DTO conversion.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StageMapper extends BaseMapper<Stage, StageDto> {

  @Mapping(target = "competitionId", source = "competition.id")
  @Mapping(target = "competitionDisplayName", source = "competition.displayName")
  @Mapping(target = "stageFormatId", source = "stageFormat.id")
  @Mapping(target = "stageFormatDisplayName", source = "stageFormat.displayName")
  @Mapping(target = "status", expression = "java(statusToString(stage.getStatus()))")
  StageDto toDto(Stage stage);

  @Mapping(target = "competition", ignore = true)
  @Mapping(target = "stageFormat", ignore = true)
  @Mapping(target = "status", expression = "java(stringToStatus(stageDto.getStatus()))")
  Stage toEntity(StageDto stageDto);

  default String statusToString(StageStatus status) {
    return status != null ? status.getLabel() : null;
  }

  default StageStatus stringToStatus(String status) {
    if (status == null)
      return null;
    for (StageStatus s : StageStatus.values()) {
      if (s.getLabel().equals(status)) {
        return s;
      }
    }
    throw new IllegalArgumentException("Invalid stage status: " + status);
  }
}
