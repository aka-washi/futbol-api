package com.eagle.futbolapi.features.lineup.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.lineup.entity.Lineup;
import com.eagle.futbolapi.features.lineup.repository.LineupRepository;
import com.eagle.futbolapi.features.match.service.MatchService;
import com.eagle.futbolapi.features.player.entity.Player;
import com.eagle.futbolapi.features.player.service.PlayerService;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.shared.service.BaseCrudService;
import com.eagle.futbolapi.features.team.entity.Team;
import com.eagle.futbolapi.features.team.service.TeamService;

@Service
@Transactional
public class LineupService extends BaseCrudService<Lineup, Long> {

    private final LineupRepository lineupRepository;
    private final MatchService matchService;
    private final TeamService teamService;
    private final PlayerService playerService;

    public LineupService(LineupRepository lineupRepository, MatchService matchService, TeamService teamService, PlayerService playerService) {
        super(lineupRepository);
        this.lineupRepository = lineupRepository;
        this.matchService = matchService;
        this.teamService = teamService;
        this.playerService = playerService;
    }

    // Helper methods to resolve dependencies by name/displayName
    // Note: Match doesn't have a simple name field, so we skip that for now

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

    public Optional<Player> resolvePlayerByDisplayName(String displayName) {
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Player display name cannot be null or empty");
        }
        return playerService.searchPlayersByDisplayName(displayName)
                .stream().findFirst();
    }

    @Override
    public Lineup update(Long id, Lineup lineup) {
        Lineup existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entity with given ID does not exist"));
        lineup.setCreatedAt(existing.getCreatedAt());
        lineup.setId(id);
        return super.update(id, lineup);
    }

    @Override
    protected boolean isDuplicate(@NotNull Lineup lineup) {
        Objects.requireNonNull(lineup, "Lineup cannot be null");

        // For now, no strict duplicate check - business logic specific
        return false;
    }

    @Override
    protected boolean isDuplicate(Long id, @NotNull Lineup lineup) {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(lineup, "Lineup details cannot be null");

        Lineup existingLineup = getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lineup", "id", id));

        // For updates, no strict duplicate check - business logic specific
        return false;
    }
}
