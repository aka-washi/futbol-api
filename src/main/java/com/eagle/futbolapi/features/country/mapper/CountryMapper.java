
package com.eagle.futbolapi.features.country.mapper;

import org.springframework.stereotype.Component;

import com.eagle.futbolapi.features.country.dto.CountryDTO;
import com.eagle.futbolapi.features.country.entity.Country;

@Component
public class CountryMapper {

    public CountryDTO toCountryDTO(Country country) {
        if (country == null) {
            return null;
        }
        return CountryDTO.builder()
                .id(country.getId())
                .name(country.getName())
                .code(country.getCode())
                .isoCode(country.getIsoCode())
                .displayName(country.getDisplayName())
                .flagUrl(country.getFlagUrl())
                .createdAt(country.getCreatedAt())
                .createdBy(country.getCreatedBy())
                .updatedAt(country.getUpdatedAt())
                .updatedBy(country.getUpdatedBy())
                .build();
    }

    public Country toCountry(CountryDTO dto) {
        if (dto == null) {
            return null;
        }
        return Country.builder()
                .id(dto.getId())
                .name(dto.getName())
                .code(dto.getCode())
                .isoCode(dto.getIsoCode())
                .displayName(dto.getDisplayName())
                .flagUrl(dto.getFlagUrl())
                .createdAt(dto.getCreatedAt())
                .createdBy(dto.getCreatedBy())
                .updatedAt(dto.getUpdatedAt())
                .updatedBy(dto.getUpdatedBy())
                .build();
    }
}
