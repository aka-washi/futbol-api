package com.eagle.futbolapi.features.standing.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.shared.service.BaseCrudService;
import com.eagle.futbolapi.features.stage.entity.Stage;
import com.eagle.futbolapi.features.stage.service.StageService;
import com.eagle.futbolapi.features.standing.entity.Standing;
import com.eagle.futbolapi.features.standing.repository.StandingRepository;
import com.eagle.futbolapi.features.team.entity.Team;
import com.eagle.futbolapi.features.team.service.TeamService;

@Service
@Transactional
public class StandingService extends BaseCrudService<Standing, Long> {

    private final StandingRepository standingRepository;
    private final StageService stageService;
    private final TeamService teamService;

    public StandingService(StandingRepository standingRepository, StageService stageService, TeamService teamService) {
        super(standingRepository);
        this.standingRepository = standingRepository;
        this.stageService = stageService;
        this.teamService = teamService;
    }

    // Helper methods to resolve dependencies by name/displayName
    public Optional<Stage> resolveStageByName(String stageName) {
        if (stageName == null || stageName.trim().isEmpty()) {
            throw new IllegalArgumentException("Stage name cannot be null or empty");
        }
        return stageService.getStageByName(stageName);
    }

    public Optional<Stage> resolveStageByDisplayName(String displayName) {
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Stage display name cannot be null or empty");
        }
        return stageService.getStageByDisplayName(displayName);
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
    public Standing update(Long id, Standing standing) {
        Standing existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entity with given ID does not exist"));
        standing.setCreatedAt(existing.getCreatedAt());
        standing.setId(id);
        return super.update(id, standing);
    }

    @Override
    protected boolean isDuplicate(@NotNull Standing standing) {
        Objects.requireNonNull(standing, "Standing cannot be null");

        // For now, no strict duplicate check - business logic specific
        return false;
    }

    @Override
    protected boolean isDuplicate(Long id, @NotNull Standing standing) {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(standing, "Standing details cannot be null");

        Standing existingStanding = getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Standing", "id", id));

        // For updates, no strict duplicate check - business logic specific
        return false;
    }
}
