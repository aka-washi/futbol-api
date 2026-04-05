package com.eagle.futbolapi.features.teamvenue.service;

import java.util.Map;
import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.team.service.TeamService;
import com.eagle.futbolapi.features.teamvenue.dto.TeamVenueDto;
import com.eagle.futbolapi.features.teamvenue.entity.TeamVenue;
import com.eagle.futbolapi.features.teamvenue.mapper.TeamVenueMapper;
import com.eagle.futbolapi.features.teamvenue.repository.TeamVenueRepository;
import com.eagle.futbolapi.features.venue.service.VenueService;

@Service
@Transactional
@Validated
public class TeamVenueService extends BaseCrudService<TeamVenue, Long, TeamVenueDto> {

  private final TeamService teamService;
  private final VenueService venueService;

  public TeamVenueService(TeamVenueRepository repository, TeamVenueMapper mapper,
      TeamService teamService, VenueService venueService) {
    super(repository, mapper);
    this.teamService = teamService;
    this.venueService = venueService;
  }

  @Override
  protected void resolveRelationships(TeamVenueDto dto, TeamVenue entity) {
    if (dto.getTeamId() != null) {
      var team = teamService.getById(dto.getTeamId())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "id", dto.getTeamId()));
      entity.setTeam(team);
    } else if (dto.getTeamDisplayName() != null && !dto.getTeamDisplayName().trim().isEmpty()) {
      var team = teamService.findByDisplayName(dto.getTeamDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "displayName", dto.getTeamDisplayName()));
      entity.setTeam(team);
    }

    if (dto.getVenueId() != null) {
      var venue = venueService.getById(dto.getVenueId())
          .orElseThrow(() -> new ResourceNotFoundException("Venue", "id", dto.getVenueId()));
      entity.setVenue(venue);
    } else if (dto.getVenueDisplayName() != null && !dto.getVenueDisplayName().trim().isEmpty()) {
      var venue = venueService.findByDisplayName(dto.getVenueDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Venue", "displayName", dto.getVenueDisplayName()));
      entity.setVenue(venue);
    }
  }

  @Override
  protected boolean isDuplicate(@NotNull TeamVenue entity) {
    Objects.requireNonNull(entity, "TeamVenue cannot be null");
    if (entity.getTeam() != null && entity.getVenue() != null) {
      return existsByUniqueFields(Map.of(
          "team.id", entity.getTeam().getId(),
          "venue.id", entity.getVenue().getId()));
    }
    return false;
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull TeamVenue entity) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(entity, "TeamVenue cannot be null");
    if (entity.getTeam() != null && entity.getVenue() != null) {
      return existsByUniqueFieldsAndNotId(Map.of(
          "team.id", entity.getTeam().getId(),
          "venue.id", entity.getVenue().getId()), id);
    }
    return false;
  }

}
