package com.eagle.futbolapi.features.match.service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.match.dto.MatchDTO;
import com.eagle.futbolapi.features.match.entity.Match;
import com.eagle.futbolapi.features.match.mapper.MatchMapper;
import com.eagle.futbolapi.features.match.repository.MatchRepository;
import com.eagle.futbolapi.features.matchday.service.MatchdayService;
import com.eagle.futbolapi.features.person.service.PersonService;
import com.eagle.futbolapi.features.team.service.TeamService;
import com.eagle.futbolapi.features.venue.service.VenueService;

@Service
@Transactional
public class MatchService extends BaseCrudService<Match, Long, MatchDTO> {

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
  protected void resolveRelationships(@NotNull MatchDTO dto, @NotNull Match match) {
    // Map Matchday from display name or ID
    if (dto.getMatchdayDisplayName() != null && !dto.getMatchdayDisplayName().trim().isEmpty()) {
      String displayName = dto.getMatchdayDisplayName();
      var matchday = matchdayService.getByDisplayName(displayName)
          .orElseThrow(() -> new ResourceNotFoundException(
              "Matchday with display name " + displayName + " not found"));
      match.setMatchday(matchday);
    }

    // Map home team from display name or ID
    if (dto.getHomeTeamDisplayName() != null && !dto.getHomeTeamDisplayName().trim().isEmpty()) {
      String homeTeamDisplayName = dto.getHomeTeamDisplayName();
      var homeTeam = teamService.getByDisplayName(homeTeamDisplayName)
          .orElseThrow(() -> new ResourceNotFoundException(
              "Home team with display name " + homeTeamDisplayName + " not found"));
      match.setHomeTeam(homeTeam);
    }

    // Map away team from display name or ID
    if (dto.getAwayTeamDisplayName() != null && !dto.getAwayTeamDisplayName().trim().isEmpty()) {
      String awayTeamDisplayName = dto.getAwayTeamDisplayName();
      var awayTeam = teamService.getByDisplayName(awayTeamDisplayName)
          .orElseThrow(() -> new ResourceNotFoundException(
              "Away team with display name " + awayTeamDisplayName + " not found"));
      match.setAwayTeam(awayTeam);
    }

    // Map venue from display name
    if (dto.getVenueDisplayName() != null && !dto.getVenueDisplayName().trim().isEmpty()) {
      String venueDisplayName = dto.getVenueDisplayName();
      var venue = venueService.getVenueByDisplayName(venueDisplayName)
          .orElseThrow(() -> new ResourceNotFoundException(
              "Venue with display name " + venueDisplayName + " not found"));
      match.setVenue(venue);
    }

    // Map referee from display name
    if (dto.getRefereeDisplayName() != null && !dto.getRefereeDisplayName().trim().isEmpty()) {
      String refereeDisplayName = dto.getRefereeDisplayName();
      var referee = personService.getByDisplayName(refereeDisplayName)
          .orElseThrow(() -> new ResourceNotFoundException(
              "Referee with display name " + refereeDisplayName + " not found"));
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
