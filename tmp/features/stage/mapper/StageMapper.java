package com.eagle.futbolapi.features.stage.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.stage.dto.StageDTO;
import com.eagle.futbolapi.features.stage.entity.Stage;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StageMapper extends BaseMapper<Stage, StageDTO> {

  @Mapping(target = "competitionId", source = "competition.id")
  @Mapping(target = "competitionDisplayName", source = "competition.displayName")
  @Mapping(target = "structureId", source = "structure.id")
  @Mapping(target = "structureDisplayName", source = "structure.displayName")
  StageDTO toDTO(Stage stage);

  @Mapping(target = "competition", ignore = true)
  @Mapping(target = "structure", ignore = true)
  Stage toEntity(StageDTO stageDTO);

}
