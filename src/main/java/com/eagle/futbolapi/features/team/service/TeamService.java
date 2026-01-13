package com.eagle.futbolapi.features.team.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.country.entity.Country;
import com.eagle.futbolapi.features.country.service.CountryService;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.shared.service.BaseCrudService;
import com.eagle.futbolapi.features.team.entity.Team;
import com.eagle.futbolapi.features.team.repository.TeamRepository;
import com.eagle.futbolapi.features.venue.entity.Venue;
import com.eagle.futbolapi.features.venue.service.VenueService;

@Service
@Transactional
public class TeamService extends BaseCrudService<Team, Long> {

    private final TeamRepository teamRepository;
    private final CountryService countryService;
    private final VenueService venueService;

    public TeamService(TeamRepository teamRepository, CountryService countryService, VenueService venueService) {
        super(teamRepository);
        this.teamRepository = teamRepository;
        this.countryService = countryService;
        this.venueService = venueService;
    }

    // Helper methods to resolve dependencies by name/displayName
    public Optional<Country> resolveCountryByName(String countryName) {
        if (countryName == null || countryName.trim().isEmpty()) {
            throw new IllegalArgumentException("Country name cannot be null or empty");
        }
        return countryService.getCountryByName(countryName);
    }

    public Optional<Country> resolveCountryByDisplayName(String displayName) {
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Country display name cannot be null or empty");
        }
        return countryService.getCountryByDisplayName(displayName);
    }

    public Optional<Country> resolveCountryByIsoCode(String isoCode) {
        if (isoCode == null || isoCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Country ISO code cannot be null or empty");
        }
        return countryService.getCountryByIsoCode(isoCode);
    }

    public Optional<Venue> resolveVenueByName(String venueName) {
        if (venueName == null || venueName.trim().isEmpty()) {
            throw new IllegalArgumentException("Venue name cannot be null or empty");
        }
        return venueService.getVenueByName(venueName);
    }

    public Optional<Venue> resolveVenueByDisplayName(String displayName) {
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Venue display name cannot be null or empty");
        }
        return venueService.getVenueByDisplayName(displayName);
    }

    public Optional<Team> getTeamByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Team name cannot be null or empty");
        }
        return teamRepository.findByName(name);
    }

    public Optional<Team> getTeamByDisplayName(String displayName) {
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Team display name cannot be null or empty");
        }
        return teamRepository.findByDisplayName(displayName);
    }

    public Optional<Team> getTeamByCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Team code cannot be null or empty");
        }
        return teamRepository.findByCode(code);
    }

    public List<Team> getTeamsByCountry(Country country) {
        if (country == null) {
            throw new IllegalArgumentException("Country cannot be null");
        }
        return teamRepository.findByCountry(country);
    }

    public List<Team> getTeamsByCountryId(Long countryId) {
        if (countryId == null) {
            throw new IllegalArgumentException("Country ID cannot be null");
        }
        return teamRepository.findByCountryId(countryId);
    }

    public List<Team> searchTeamsByName(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            throw new IllegalArgumentException("Search term cannot be null or empty");
        }
        return teamRepository.findByNameContainingIgnoreCase(searchTerm);
    }

    @Override
    public Team update(Long id, Team team) {
        Team existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entity with given ID does not exist"));
        team.setCreatedAt(existing.getCreatedAt());
        team.setId(id);
        return super.update(id, team);
    }

    @Override
    protected boolean isDuplicate(@NotNull Team team) {
        Objects.requireNonNull(team, "Team cannot be null");

        // Teams can have similar names across different countries/leagues
        // For now, no strict duplicate check - business logic specific
        return false;
    }

    @Override
    protected boolean isDuplicate(Long id, @NotNull Team team) {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(team, "Team details cannot be null");

        Team existingTeam = getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team", "id", id));

        // For updates, no strict duplicate check - business logic specific
        return false;
    }
}
