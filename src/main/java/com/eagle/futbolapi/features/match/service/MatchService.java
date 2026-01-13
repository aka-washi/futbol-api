package com.eagle.futbolapi.features.match.service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.match.entity.Match;
import com.eagle.futbolapi.features.match.entity.MatchStatus;
import com.eagle.futbolapi.features.match.repository.MatchRepository;
import com.eagle.futbolapi.features.matchday.entity.Matchday;
import com.eagle.futbolapi.features.matchday.service.MatchdayService;
import com.eagle.futbolapi.features.person.entity.Person;
import com.eagle.futbolapi.features.person.service.PersonService;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.shared.service.BaseCrudService;
import com.eagle.futbolapi.features.team.entity.Team;
import com.eagle.futbolapi.features.team.service.TeamService;
import com.eagle.futbolapi.features.venue.entity.Venue;
import com.eagle.futbolapi.features.venue.service.VenueService;

@Service
@Transactional
public class MatchService extends BaseCrudService<Match, Long> {

    private final MatchRepository matchRepository;
    private final MatchdayService matchdayService;
    private final TeamService teamService;
    private final VenueService venueService;
    private final PersonService personService;

    public MatchService(MatchRepository matchRepository, MatchdayService matchdayService,
                       TeamService teamService, VenueService venueService, PersonService personService) {
        super(matchRepository);
        this.matchRepository = matchRepository;
        this.matchdayService = matchdayService;
        this.teamService = teamService;
        this.venueService = venueService;
        this.personService = personService;
    }

    // Helper methods to resolve dependencies by name/displayName
    public Optional<Matchday> resolveMatchdayByName(String matchdayName) {
        if (matchdayName == null || matchdayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Matchday name cannot be null or empty");
        }
        return matchdayService.getMatchdayByName(matchdayName);
    }

    public Optional<Matchday> resolveMatchdayByDisplayName(String displayName) {
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Matchday display name cannot be null or empty");
        }
        return matchdayService.getMatchdayByDisplayName(displayName);
    }

    public Optional<Team> resolveTeamByName(String teamName) {
        if (teamName == null || teamName.trim().isEmpty()) {
            throw new IllegalArgumentException("Team name cannot be null or empty");
        }
        return teamService.getTeamByName(teamName);
    }

    public Optional<Team> resolveTeamByDisplayName(String displayName) {
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Team display name cannot be null or empty");
        }
        return teamService.getTeamByDisplayName(displayName);
    }

    public Optional<Team> resolveTeamByCode(String teamCode) {
        if (teamCode == null || teamCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Team code cannot be null or empty");
        }
        return teamService.getTeamByCode(teamCode);
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

    public Optional<Person> resolveRefereeByDisplayName(String displayName) {
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Referee display name cannot be null or empty");
        }
        return personService.searchPersonsByDisplayName(displayName, null)
                .getContent().stream().findFirst();
    }

    public Page<Match> getMatchesByMatchday(Matchday matchday, Pageable pageable) {
        if (matchday == null) {
            throw new IllegalArgumentException("Matchday cannot be null");
        }
        return matchRepository.findByMatchday(matchday, pageable);
    }

    public Page<Match> getMatchesByMatchdayId(Long matchdayId, Pageable pageable) {
        if (matchdayId == null) {
            throw new IllegalArgumentException("Matchday ID cannot be null");
        }
        return matchRepository.findByMatchdayId(matchdayId, pageable);
    }

    public Page<Match> getMatchesByHomeTeam(Team homeTeam, Pageable pageable) {
        if (homeTeam == null) {
            throw new IllegalArgumentException("Home team cannot be null");
        }
        return matchRepository.findByHomeTeam(homeTeam, pageable);
    }

    public Page<Match> getMatchesByHomeTeamId(Long homeTeamId, Pageable pageable) {
        if (homeTeamId == null) {
            throw new IllegalArgumentException("Home team ID cannot be null");
        }
        return matchRepository.findByHomeTeamId(homeTeamId, pageable);
    }

    public Page<Match> getMatchesByAwayTeam(Team awayTeam, Pageable pageable) {
        if (awayTeam == null) {
            throw new IllegalArgumentException("Away team cannot be null");
        }
        return matchRepository.findByAwayTeam(awayTeam, pageable);
    }

    public Page<Match> getMatchesByAwayTeamId(Long awayTeamId, Pageable pageable) {
        if (awayTeamId == null) {
            throw new IllegalArgumentException("Away team ID cannot be null");
        }
        return matchRepository.findByAwayTeamId(awayTeamId, pageable);
    }

    public Page<Match> getMatchesByStatus(MatchStatus status, Pageable pageable) {
        if (status == null) {
            throw new IllegalArgumentException("Match status cannot be null");
        }
        return matchRepository.findByStatus(status, pageable);
    }

    public Page<Match> getMatchesByDate(LocalDate date, Pageable pageable) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        return matchRepository.findByScheduledDate(date, pageable);
    }

    public Page<Match> getMatchesByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        if (startDate == null) {
            throw new IllegalArgumentException("Start date cannot be null");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("End date cannot be null");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        return matchRepository.findByDateRange(startDate, endDate, pageable);
    }

    public Page<Match> getMatchesByTeamId(Long teamId, Pageable pageable) {
        if (teamId == null) {
            throw new IllegalArgumentException("Team ID cannot be null");
        }
        return matchRepository.findByTeamId(teamId, pageable);
    }

    public Page<Match> getMatchesByTeams(Long homeTeamId, Long awayTeamId, Pageable pageable) {
        if (homeTeamId == null) {
            throw new IllegalArgumentException("Home team ID cannot be null");
        }
        if (awayTeamId == null) {
            throw new IllegalArgumentException("Away team ID cannot be null");
        }
        return matchRepository.findByTeams(homeTeamId, awayTeamId, pageable);
    }

    public Page<Match> getMatchesByStageId(Long stageId, Pageable pageable) {
        if (stageId == null) {
            throw new IllegalArgumentException("Stage ID cannot be null");
        }
        return matchRepository.findByStageId(stageId, pageable);
    }

    public Page<Match> getMatchesByHomeTeamAndAwayTeam(Long homeTeamId, Long awayTeamId, Pageable pageable) {
        if (homeTeamId == null) {
            throw new IllegalArgumentException("Home team ID cannot be null");
        }
        if (awayTeamId == null) {
            throw new IllegalArgumentException("Away team ID cannot be null");
        }
        return matchRepository.findByHomeTeamIdAndAwayTeamId(homeTeamId, awayTeamId, pageable);
    }

    public Page<Match> getMatchesByVenue(Venue venue, Pageable pageable) {
        if (venue == null) {
            throw new IllegalArgumentException("Venue cannot be null");
        }
        return matchRepository.findByVenue(venue, pageable);
    }

    public Page<Match> getMatchesByVenueId(Long venueId, Pageable pageable) {
        if (venueId == null) {
            throw new IllegalArgumentException("Venue ID cannot be null");
        }
        return matchRepository.findByVenueId(venueId, pageable);
    }

    public Page<Match> getMatchesByReferee(Person referee, Pageable pageable) {
        if (referee == null) {
            throw new IllegalArgumentException("Referee cannot be null");
        }
        return matchRepository.findByReferee(referee, pageable);
    }

    public Page<Match> getMatchesByRefereeId(Long refereeId, Pageable pageable) {
        if (refereeId == null) {
            throw new IllegalArgumentException("Referee ID cannot be null");
        }
        return matchRepository.findByRefereeId(refereeId, pageable);
    }

    @Override
    public Match update(Long id, Match match) {
        Match existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entity with given ID does not exist"));
        match.setCreatedAt(existing.getCreatedAt());
        match.setId(id);
        return super.update(id, match);
    }

    @Override
    protected boolean isDuplicate(@NotNull Match match) {
        Objects.requireNonNull(match, "Match cannot be null");

        // For now, no strict duplicate check - business logic can be complex for
        // matches
        return false;
    }

    @Override
    protected boolean isDuplicate(Long id, @NotNull Match match) {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(match, "Match details cannot be null");

        Match existingMatch = getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Match", "id", id));

        // For updates, no strict duplicate check - business logic can be complex for
        // matches
        return false;
    }
}
