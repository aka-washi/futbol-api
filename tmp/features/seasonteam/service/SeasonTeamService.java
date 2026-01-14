package com.eagle.futbolapi.features.seasonteam.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.season.entity.Season;
import com.eagle.futbolapi.features.season.service.SeasonService;
import com.eagle.futbolapi.features.seasonteam.entity.SeasonTeam;
import com.eagle.futbolapi.features.seasonteam.repository.SeasonTeamRepository;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.shared.service.BaseCrudService;
import com.eagle.futbolapi.features.team.entity.Team;
import com.eagle.futbolapi.features.team.service.TeamService;

@Service
@Transactional
public class SeasonTeamService extends BaseCrudService<SeasonTeam, Long> {

    private final SeasonTeamRepository seasonTeamRepository;
    private final SeasonService seasonService;
    private final TeamService teamService;

    public SeasonTeamService(SeasonTeamRepository seasonTeamRepository, SeasonService seasonService, TeamService teamService) {
        super(seasonTeamRepository);
        this.seasonTeamRepository = seasonTeamRepository;
        this.seasonService = seasonService;
        this.teamService = teamService;
    }

    // Helper methods to resolve dependencies by name/displayName
    public Optional<Season> resolveSeasonByName(String seasonName) {
        if (seasonName == null || seasonName.trim().isEmpty()) {
            throw new IllegalArgumentException("Season name cannot be null or empty");
        }
        return seasonService.getSeasonByName(seasonName);
    }

    public Optional<Season> resolveSeasonByDisplayName(String displayName) {
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Season display name cannot be null or empty");
        }
        return seasonService.getSeasonByDisplayName(displayName);
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

    @Override
    public SeasonTeam update(Long id, SeasonTeam seasonTeam) {
        SeasonTeam existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entity with given ID does not exist"));
        seasonTeam.setCreatedAt(existing.getCreatedAt());
        seasonTeam.setId(id);
        return super.update(id, seasonTeam);
    }

    @Override
    protected boolean isDuplicate(@NotNull SeasonTeam seasonTeam) {
        Objects.requireNonNull(seasonTeam, "SeasonTeam cannot be null");

        // For now, no strict duplicate check - business logic specific
        return false;
    }

    @Override
    protected boolean isDuplicate(Long id, @NotNull SeasonTeam seasonTeam) {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(seasonTeam, "SeasonTeam details cannot be null");

        SeasonTeam existingSeasonTeam = getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SeasonTeam", "id", id));

        // For updates, no strict duplicate check - business logic specific
        return false;
    }
}
