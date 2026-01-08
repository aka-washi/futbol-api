package com.eagle.futbolapi.features.seasonteam.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.seasonteam.entity.SeasonTeam;
import com.eagle.futbolapi.features.seasonteam.repository.SeasonTeamRepository;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.shared.service.BaseCrudService;

@Service
@Transactional
public class SeasonTeamService extends BaseCrudService<SeasonTeam, Long> {

    private final SeasonTeamRepository seasonTeamRepository;

    public SeasonTeamService(SeasonTeamRepository seasonTeamRepository) {
        super(seasonTeamRepository);
        this.seasonTeamRepository = seasonTeamRepository;
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
