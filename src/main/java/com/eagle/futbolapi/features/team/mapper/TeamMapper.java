package com.eagle.futbolapi.features.team.mapper;

import org.springframework.stereotype.Component;

import com.eagle.futbolapi.features.country.repository.CountryRepository;
import com.eagle.futbolapi.features.team.dto.TeamDTO;
import com.eagle.futbolapi.features.team.entity.Team;
import com.eagle.futbolapi.features.venue.repository.VenueRepository;

@Component
public class TeamMapper {

    private final CountryRepository countryRepository;
    private final VenueRepository venueRepository;

    public TeamMapper(CountryRepository countryRepository, VenueRepository venueRepository) {
        this.countryRepository = countryRepository;
        this.venueRepository = venueRepository;
    }

    public TeamDTO toTeamDTO(Team team) {
        if (team == null) {
            return null;
        }

        return TeamDTO.builder()
                .id(team.getId())
                .name(team.getName())
                .displayName(team.getDisplayName())
                .code(team.getCode())
                .founded(team.getFounded())
                .countryId(team.getCountry() != null ? team.getCountry().getId() : null)
                .countryDisplayName(team.getCountry() != null ? team.getCountry().getDisplayName() : null)
                .homeVenueId(team.getHomeVenue() != null ? team.getHomeVenue().getId() : null)
                .homeVenueName(team.getHomeVenue() != null ? team.getHomeVenue().getName() : null)
                .logo(team.getLogo())
                .primaryColor(team.getPrimaryColor())
                .secondaryColor(team.getSecondaryColor())
                .website(team.getWebsite())
                .active(team.getActive())
                .createdAt(team.getCreatedAt())
                .createdBy(team.getCreatedBy())
                .updatedAt(team.getUpdatedAt())
                .updatedBy(team.getUpdatedBy())
                .build();
    }

    public Team toTeam(TeamDTO dto) {
        if (dto == null) {
            return null;
        }

        var builder = Team.builder()
                .id(dto.getId())
                .name(dto.getName())
                .displayName(dto.getDisplayName())
                .code(dto.getCode())
                .founded(dto.getFounded())
                .logo(dto.getLogo())
                .primaryColor(dto.getPrimaryColor())
                .secondaryColor(dto.getSecondaryColor())
                .website(dto.getWebsite())
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

        if (dto.getHomeVenueId() != null) {
            var venue = venueRepository.findById(dto.getHomeVenueId())
                    .orElse(null);
            builder.homeVenue(venue);
        }

        return builder.build();
    }
}
