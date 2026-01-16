package com.eagle.futbolapi.features.venue.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.venue.dto.VenueDTO;
import com.eagle.futbolapi.features.venue.entity.Venue;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VenueMapper {

  @Mapping(target = "countryId", source = "country.id")
  @Mapping(target = "countryDisplayName", source = "country.displayName")
  VenueDTO toVenueDTO(Venue venue);

  @Mapping(target = "country", ignore = true)
  Venue toVenue(VenueDTO venueDTO);

}
