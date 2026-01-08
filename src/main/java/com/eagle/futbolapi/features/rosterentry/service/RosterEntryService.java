package com.eagle.futbolapi.features.rosterentry.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.rosterentry.entity.RosterEntry;
import com.eagle.futbolapi.features.rosterentry.repository.RosterEntryRepository;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.shared.service.BaseCrudService;

@Service
@Transactional
public class RosterEntryService extends BaseCrudService<RosterEntry, Long> {

    private final RosterEntryRepository rosterEntryRepository;

    public RosterEntryService(RosterEntryRepository rosterEntryRepository) {
        super(rosterEntryRepository);
        this.rosterEntryRepository = rosterEntryRepository;
    }

    @Override
    public RosterEntry update(Long id, RosterEntry rosterEntry) {
        RosterEntry existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entity with given ID does not exist"));
        rosterEntry.setCreatedAt(existing.getCreatedAt());
        rosterEntry.setId(id);
        return super.update(id, rosterEntry);
    }

    @Override
    protected boolean isDuplicate(@NotNull RosterEntry rosterEntry) {
        Objects.requireNonNull(rosterEntry, "RosterEntry cannot be null");

        // For now, no strict duplicate check - business logic specific
        return false;
    }

    @Override
    protected boolean isDuplicate(Long id, @NotNull RosterEntry rosterEntry) {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(rosterEntry, "RosterEntry details cannot be null");

        RosterEntry existingRosterEntry = getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RosterEntry", "id", id));

        // For updates, no strict duplicate check - business logic specific
        return false;
    }
}
