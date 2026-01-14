package com.eagle.futbolapi.features.matchevent.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.matchevent.entity.MatchEvent;
import com.eagle.futbolapi.features.matchevent.repository.MatchEventRepository;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.shared.service.BaseCrudService;

@Service
@Transactional
public class MatchEventService extends BaseCrudService<MatchEvent, Long> {

    private final MatchEventRepository matchEventRepository;

    public MatchEventService(MatchEventRepository matchEventRepository) {
        super(matchEventRepository);
        this.matchEventRepository = matchEventRepository;
    }

    @Override
    public MatchEvent update(Long id, MatchEvent matchEvent) {
        MatchEvent existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entity with given ID does not exist"));
        matchEvent.setCreatedAt(existing.getCreatedAt());
        matchEvent.setId(id);
        return super.update(id, matchEvent);
    }

    @Override
    protected boolean isDuplicate(@NotNull MatchEvent matchEvent) {
        Objects.requireNonNull(matchEvent, "MatchEvent cannot be null");

        // For now, no strict duplicate check - business logic specific
        return false;
    }

    @Override
    protected boolean isDuplicate(Long id, @NotNull MatchEvent matchEvent) {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(matchEvent, "MatchEvent details cannot be null");

        MatchEvent existingMatchEvent = getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MatchEvent", "id", id));

        // For updates, no strict duplicate check - business logic specific
        return false;
    }
}
