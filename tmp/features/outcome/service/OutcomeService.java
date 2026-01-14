package com.eagle.futbolapi.features.outcome.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.outcome.entity.Outcome;
import com.eagle.futbolapi.features.outcome.repository.OutcomeRepository;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.shared.service.BaseCrudService;

@Service
@Transactional
public class OutcomeService extends BaseCrudService<Outcome, Long> {

    private final OutcomeRepository outcomeRepository;

    public OutcomeService(OutcomeRepository outcomeRepository) {
        super(outcomeRepository);
        this.outcomeRepository = outcomeRepository;
    }

    @Override
    public Outcome update(Long id, Outcome outcome) {
        Outcome existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entity with given ID does not exist"));
        outcome.setCreatedAt(existing.getCreatedAt());
        outcome.setId(id);
        return super.update(id, outcome);
    }

    @Override
    protected boolean isDuplicate(@NotNull Outcome outcome) {
        Objects.requireNonNull(outcome, "Outcome cannot be null");

        // For now, no strict duplicate check - business logic specific
        return false;
    }

    @Override
    protected boolean isDuplicate(Long id, @NotNull Outcome outcome) {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(outcome, "Outcome details cannot be null");

        Outcome existingOutcome = getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Outcome", "id", id));

        // For updates, no strict duplicate check - business logic specific
        return false;
    }
}
