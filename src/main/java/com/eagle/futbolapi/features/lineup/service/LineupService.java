package com.eagle.futbolapi.features.lineup.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.lineup.entity.Lineup;
import com.eagle.futbolapi.features.lineup.repository.LineupRepository;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.shared.service.BaseCrudService;

@Service
@Transactional
public class LineupService extends BaseCrudService<Lineup, Long> {

    private final LineupRepository lineupRepository;

    public LineupService(LineupRepository lineupRepository) {
        super(lineupRepository);
        this.lineupRepository = lineupRepository;
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
