package com.eagle.futbolapi.features.team.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.shared.service.BaseCrudService;
import com.eagle.futbolapi.features.team.entity.Team;
import com.eagle.futbolapi.features.team.repository.TeamRepository;

@Service
@Transactional
public class TeamService extends BaseCrudService<Team, Long> {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        super(teamRepository);
        this.teamRepository = teamRepository;
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
