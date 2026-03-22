package com.eagle.futbolapi.features.pointsystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.pointsystem.dto.PointSystemDto;
import com.eagle.futbolapi.features.pointsystem.entity.PointSystem;

/**
 * MapStruct mapper for PointSystem entity and DTO conversion.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PointSystemMapper extends BaseMapper<PointSystem, PointSystemDto> {

  PointSystemDto toDto(PointSystem pointSystem);

  PointSystem toEntity(PointSystemDto pointSystemDto);

}
