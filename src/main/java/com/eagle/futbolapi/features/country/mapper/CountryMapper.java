package com.eagle.futbolapi.features.country.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.country.dto.CountryDto;
import com.eagle.futbolapi.features.country.entity.Country;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CountryMapper extends BaseMapper<Country, CountryDto> {

  CountryDto toDTO(Country country);

  Country toEntity(CountryDto countryDTO);
}
