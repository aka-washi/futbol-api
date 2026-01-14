package com.eagle.futbolapi.features.season.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.season.entity.Season;
import com.eagle.futbolapi.features.season.repository.SeasonRepository;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.shared.service.BaseCrudService;

@Service
@Transactional
public class SeasonService extends BaseCrudService<Season, Long> {

    private final SeasonRepository seasonRepository;

    public SeasonService(SeasonRepository seasonRepository) {
        super(seasonRepository);
        this.seasonRepository = seasonRepository;
    }

    public Optional<Season> getSeasonByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Season name cannot be null or empty");
        }
        return seasonRepository.findByName(name);
    }

    public Optional<Season> getSeasonByDisplayName(String displayName) {
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Season display name cannot be null or empty");
        }
        return seasonRepository.findByDisplayName(displayName);
    }

    @Override
    public Season update(Long id, Season season) {
        Season existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entity with given ID does not exist"));
        season.setCreatedAt(existing.getCreatedAt());
        season.setId(id);
        return super.update(id, season);
    }

    @Override
    protected boolean isDuplicate(@NotNull Season season) {
        Objects.requireNonNull(season, "Season cannot be null");

        // For now, no strict duplicate check - business logic specific
        return false;
    }

    @Override
    protected boolean isDuplicate(Long id, @NotNull Season season) {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(season, "Season details cannot be null");

        Season existingSeason = getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Season", "id", id));

        // For updates, no strict duplicate check - business logic specific
        return false;
    }
}
