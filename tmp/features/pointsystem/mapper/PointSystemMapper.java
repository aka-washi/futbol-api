package com.eagle.futbolapi.features.pointsystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.pointsystem.dto.PointSystemDTO;
import com.eagle.futbolapi.features.pointsystem.entity.PointSystem;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PointSystemMapper extends BaseMapper<PointSystem, PointSystemDTO> {

  PointSystemDTO toDTO(PointSystem pointSystem);

  PointSystem toEntity(PointSystemDTO pointSystemDTO);

}
