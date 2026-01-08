package com.eagle.futbolapi.features.standing.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.shared.service.BaseCrudService;
import com.eagle.futbolapi.features.standing.entity.Standing;
import com.eagle.futbolapi.features.standing.repository.StandingRepository;

@Service
@Transactional
public class StandingService extends BaseCrudService<Standing, Long> {

    private final StandingRepository standingRepository;

    public StandingService(StandingRepository standingRepository) {
        super(standingRepository);
        this.standingRepository = standingRepository;
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
