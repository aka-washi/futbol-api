package com.eagle.futbolapi.features.country.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.country.dto.CountryDto;
import com.eagle.futbolapi.features.country.entity.Country;

/**
 * MapStruct mapper for converting between Country entities and CountryDto
 * objects.
 * Provides bidirectional mapping functionality with automatic field mapping.
 * 
 * <p>
 * This mapper is automatically implemented by MapStruct at compile time
 * and registered as a Spring bean through the component model configuration.
 * 
 * @see Country
 * @see CountryDto
 * @see BaseMapper
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CountryMapper extends BaseMapper<Country, CountryDto> {

  /**
   * Converts a Country entity to a CountryDto.
   * 
   * @param country the Country entity to convert
   * @return the corresponding CountryDto, or null if country is null
   */
  CountryDto toDto(Country country);

  /**
   * Converts a CountryDto to a Country entity.
   * 
   * @param countryDTO the CountryDto to convert
   * @return the corresponding Country entity, or null if countryDTO is null
   */
  Country toEntity(CountryDto countryDTO);

}
