package com.eagle.futbolapi.features.matchday.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.matchday.entity.Matchday;
import com.eagle.futbolapi.features.matchday.repository.MatchdayRepository;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.shared.service.BaseCrudService;

@Service
@Transactional
public class MatchdayService extends BaseCrudService<Matchday, Long> {

    private final MatchdayRepository matchdayRepository;

    public MatchdayService(MatchdayRepository matchdayRepository) {
        super(matchdayRepository);
        this.matchdayRepository = matchdayRepository;
    }

    public Optional<Matchday> getMatchdayByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Matchday name cannot be null or empty");
        }
        return matchdayRepository.findByName(name);
    }

    public Optional<Matchday> getMatchdayByDisplayName(String displayName) {
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Matchday display name cannot be null or empty");
        }
        return matchdayRepository.findByDisplayName(displayName);
    }

    @Override
    public Matchday update(Long id, Matchday matchday) {
        Matchday existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entity with given ID does not exist"));
        matchday.setCreatedAt(existing.getCreatedAt());
        matchday.setId(id);
        return super.update(id, matchday);
    }

    @Override
    protected boolean isDuplicate(@NotNull Matchday matchday) {
        Objects.requireNonNull(matchday, "Matchday cannot be null");

        // For now, no strict duplicate check - business logic specific
        return false;
    }

    @Override
    protected boolean isDuplicate(Long id, @NotNull Matchday matchday) {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(matchday, "Matchday details cannot be null");

        Matchday existingMatchday = getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matchday", "id", id));

        // For updates, no strict duplicate check - business logic specific
        return false;
    }
}
