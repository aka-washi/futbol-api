package com.eagle.futbolapi.features.match.service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.match.dto.MatchDto;
import com.eagle.futbolapi.features.match.entity.Match;
import com.eagle.futbolapi.features.match.mapper.MatchMapper;
import com.eagle.futbolapi.features.match.repository.MatchRepository;
import com.eagle.futbolapi.features.matchday.service.MatchdayService;
import com.eagle.futbolapi.features.person.service.PersonService;
import com.eagle.futbolapi.features.team.service.TeamService;
import com.eagle.futbolapi.features.venue.service.VenueService;

@Service
@Transactional
@Validated
public class MatchService extends BaseCrudService<Match, Long, MatchDto> {

  private final MatchRepository matchRepository;
  private final MatchdayService matchdayService;
  private final TeamService teamService;
  private final VenueService venueService;
  private final PersonService personService;

  public MatchService(MatchRepository matchRepository, MatchMapper mapper, MatchdayService matchdayService,
      TeamService teamService, VenueService venueService, PersonService personService) {
    super(matchRepository, mapper);
    this.matchRepository = matchRepository;
    this.matchdayService = matchdayService;
    this.teamService = teamService;
    this.venueService = venueService;
    this.personService = personService;
  }

  public Optional<Match> getMatchByName(String name) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Match name cannot be null or empty");
    }
    return matchRepository.findByName(name);
  }

  public Optional<Match> getMatchByDisplayName(String displayName) {
    if (displayName == null || displayName.isEmpty()) {
      throw new IllegalArgumentException("Match display name cannot be null or empty");
    }
    return matchRepository.findByDisplayName(displayName);
  }

  /**
   * Resolves related entities (Matchday, homeTeam, awayTeam, Venue, Referee) from
   * DTO.
   */
  @Override
  protected void resolveRelationships(MatchDto dto, Match match) {
    // Map Matchday from ID or display name
    if (dto.getMatchdayId() != null) {
      var matchday = matchdayService.getById(dto.getMatchdayId())
          .orElseThrow(() -> new ResourceNotFoundException("Matchday", "id", dto.getMatchdayId()));
      match.setMatchday(matchday);
    } else if (dto.getMatchdayDisplayName() != null && !dto.getMatchdayDisplayName().trim().isEmpty()) {
      var matchday = matchdayService.getByDisplayName(dto.getMatchdayDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Matchday", "displayName", dto.getMatchdayDisplayName()));
      match.setMatchday(matchday);
    }

    // Map home team from ID or display name
    if (dto.getHomeTeamId() != null) {
      var homeTeam = teamService.getById(dto.getHomeTeamId())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "id", dto.getHomeTeamId()));
      match.setHomeTeam(homeTeam);
    } else if (dto.getHomeTeamDisplayName() != null && !dto.getHomeTeamDisplayName().trim().isEmpty()) {
      var homeTeam = teamService.findByDisplayName(dto.getHomeTeamDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "displayName", dto.getHomeTeamDisplayName()));
      match.setHomeTeam(homeTeam);
    }

    // Map away team from ID or display name
    if (dto.getAwayTeamId() != null) {
      var awayTeam = teamService.getById(dto.getAwayTeamId())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "id", dto.getAwayTeamId()));
      match.setAwayTeam(awayTeam);
    } else if (dto.getAwayTeamDisplayName() != null && !dto.getAwayTeamDisplayName().trim().isEmpty()) {
      var awayTeam = teamService.findByDisplayName(dto.getAwayTeamDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Team", "displayName", dto.getAwayTeamDisplayName()));
      match.setAwayTeam(awayTeam);
    }

    // Map venue from ID or display name
    if (dto.getVenueId() != null) {
      var venue = venueService.getById(dto.getVenueId())
          .orElseThrow(() -> new ResourceNotFoundException("Venue", "id", dto.getVenueId()));
      match.setVenue(venue);
    } else if (dto.getVenueDisplayName() != null && !dto.getVenueDisplayName().trim().isEmpty()) {
      var venue = venueService.findByDisplayName(dto.getVenueDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Venue", "displayName", dto.getVenueDisplayName()));
      match.setVenue(venue);
    }

    // Map referee from ID or display name
    if (dto.getRefereeId() != null) {
      var referee = personService.getById(dto.getRefereeId())
          .orElseThrow(() -> new ResourceNotFoundException("Person", "id", dto.getRefereeId()));
      match.setReferee(referee);
    } else if (dto.getRefereeDisplayName() != null && !dto.getRefereeDisplayName().trim().isEmpty()) {
      var referee = personService.findByDisplayName(dto.getRefereeDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Person", "displayName", dto.getRefereeDisplayName()));
      match.setReferee(referee);
    }
  }

  @Override
  protected boolean isDuplicate(@NotNull Match match) {
    Objects.requireNonNull(match, "Match cannot be null");

    // Check composite unique constraint: matchday + homeTeam + awayTeam
    if (match.getMatchday() != null && match.getHomeTeam() != null && match.getAwayTeam() != null) {
      return existsByUniqueFields(Map.of(
          "matchday.id", match.getMatchday().getId(),
          "homeTeam.id", match.getHomeTeam().getId(),
          "awayTeam.id", match.getAwayTeam().getId()));
    }
    return false;
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Match match) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(match, "Match cannot be null");

    // Check composite unique constraint: matchday + homeTeam + awayTeam (excluding
    // current ID)
    if (match.getMatchday() != null && match.getHomeTeam() != null && match.getAwayTeam() != null) {
      return existsByUniqueFieldsAndNotId(Map.of(
          "matchday.id", match.getMatchday().getId(),
          "homeTeam.id", match.getHomeTeam().getId(),
          "awayTeam.id", match.getAwayTeam().getId()), id);
    }
    return false;
  }

}
