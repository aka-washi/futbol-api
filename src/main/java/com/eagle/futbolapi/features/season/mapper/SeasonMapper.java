package com.eagle.futbolapi.features.season.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.season.dto.SeasonDto;
import com.eagle.futbolapi.features.season.entity.Season;

/**
 * MapStruct mapper for Season entity and DTO conversion.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SeasonMapper extends BaseMapper<Season, SeasonDto> {

  SeasonDto toDto(Season season);

  Season toEntity(SeasonDto seasonDto);

}
