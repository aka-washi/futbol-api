package com.eagle.futbolapi.features.venue.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.venue.dto.VenueDto;
import com.eagle.futbolapi.features.venue.entity.Venue;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VenueMapper extends BaseMapper<Venue, VenueDto> {

  @Mapping(target = "countryId", source = "country.id")
  @Mapping(target = "countryDisplayName", source = "country.displayName")
  VenueDto toDto(Venue venue);

  @Mapping(target = "country", ignore = true)
  Venue toEntity(VenueDto venueDto);

}
