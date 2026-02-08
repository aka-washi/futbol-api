package com.eagle.futbolapi.features.season.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.season.dto.SeasonDto;
import com.eagle.futbolapi.features.season.entity.Season;

/**
 * MapStruct mapper for converting between Season entities and SeasonDto
 * objects.
 * Provides bidirectional mapping functionality with automatic field mapping.
 *
 * <p>
 * This mapper is automatically implemented by MapStruct at compile time
 * and registered as a Spring bean through the component model configuration.
 *
 * @see Season
 * @see SeasonDto
 * @see BaseMapper
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SeasonMapper extends BaseMapper<Season, SeasonDto> {

  /**
   * Converts a Season entity to a SeasonDto.
   *
   * @param season the Season entity to convert
   * @return the corresponding SeasonDto, or null if season is null
   */
  SeasonDto toDto(Season season);

  /**
   * Converts a SeasonDto to a Season entity.
   *
   * @param seasonDto the SeasonDto to convert
   * @return the corresponding Season entity, or null if seasonDto is null
   */
  Season toEntity(SeasonDto seasonDto);

}
