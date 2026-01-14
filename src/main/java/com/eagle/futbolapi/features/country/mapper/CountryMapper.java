package com.eagle.futbolapi.features.country.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.country.dto.CountryDTO;
import com.eagle.futbolapi.features.country.entity.Country;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CountryMapper {

  CountryDTO toCountryDTO(Country country);

  Country toCountry(CountryDTO countryDTO);
}
