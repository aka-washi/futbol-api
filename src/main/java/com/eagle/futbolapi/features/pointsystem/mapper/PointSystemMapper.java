package com.eagle.futbolapi.features.pointsystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.pointsystem.dto.PointSystemDto;
import com.eagle.futbolapi.features.pointsystem.entity.PointSystem;

/**
 * MapStruct mapper for converting between Point System entities and
 * PointSystemDto objects.
 * Provides bidirectional mapping functionality with automatic field mapping.
 *
 * <p>
 * This mapper is automatically implemented by MapStruct at compile time
 * and registered as a Spring bean through the component model configuration.
 *
 * @see PointSystem
 * @see PointSystemDto
 * @see BaseMapper
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PointSystemMapper extends BaseMapper<PointSystem, PointSystemDto> {

  /**
   * Converts a PointSystem entity to a PointSystemDto.
   *
   * @param pointSystem the PointSystem entity to convert
   * @return the corresponding PointSystemDto, or null if pointSystem is null
   */
  PointSystemDto toDto(PointSystem pointSystem);

  /**
   * Converts a PointSystemDto to a PointSystem entity.
   *
   * @param pointSystemDto the PointSystemDto to convert
   * @return the corresponding PointSystem entity, or null if pointSystemDto is
   *         null
   */
  PointSystem toEntity(PointSystemDto pointSystemDto);

}
