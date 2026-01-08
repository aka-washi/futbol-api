package com.eagle.futbolapi.features.venue.mapper;

import org.springframework.stereotype.Component;

import com.eagle.futbolapi.features.country.repository.CountryRepository;
import com.eagle.futbolapi.features.venue.dto.VenueDTO;
import com.eagle.futbolapi.features.venue.entity.Venue;

@Component
public class VenueMapper {

    private final CountryRepository countryRepository;

    public VenueMapper(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public VenueDTO toVenueDTO(Venue venue) {
        if (venue == null) {
            return null;
        }

        return VenueDTO.builder()
                .id(venue.getId())
                .name(venue.getName())
                .displayName(venue.getDisplayName())
                .city(venue.getCity())
                .countryId(venue.getCountry() != null ? venue.getCountry().getId() : null)
                .countryDisplayName(venue.getCountry() != null ? venue.getCountry().getDisplayName() : null)
                .capacity(venue.getCapacity())
                .address(venue.getAddress())
                .image(venue.getImage())
                .opened(venue.getOpened())
                .surface(venue.getSurface())
                .active(venue.isActive())
                .createdAt(venue.getCreatedAt())
                .createdBy(venue.getCreatedBy())
                .updatedAt(venue.getUpdatedAt())
                .updatedBy(venue.getUpdatedBy())
                .build();
    }

    public Venue toVenue(VenueDTO dto) {
        if (dto == null) {
            return null;
        }

        var builder = Venue.builder()
                .id(dto.getId())
                .name(dto.getName())
                .displayName(dto.getDisplayName())
                .city(dto.getCity())
                .capacity(dto.getCapacity())
                .address(dto.getAddress())
                .image(dto.getImage())
                .opened(dto.getOpened())
                .surface(dto.getSurface())
                .active(dto.getActive())
                .createdAt(dto.getCreatedAt())
                .createdBy(dto.getCreatedBy())
                .updatedAt(dto.getUpdatedAt())
                .updatedBy(dto.getUpdatedBy());

        if (dto.getCountryId() != null) {
            var country = countryRepository.findById(dto.getCountryId())
                    .orElseThrow(
                            () -> new IllegalArgumentException("Country not found with id: " + dto.getCountryId()));
            builder.country(country);
        }

        return builder.build();
    }
}
