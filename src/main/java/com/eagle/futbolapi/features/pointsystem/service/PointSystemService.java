package com.eagle.futbolapi.features.pointsystem.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import com.eagle.futbolapi.features.pointsystem.entity.PointSystem;
import com.eagle.futbolapi.features.pointsystem.repository.PointSystemRepository;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.shared.service.BaseCrudService;

@Service
@Transactional
public class PointSystemService extends BaseCrudService<PointSystem, Long> {

    private final PointSystemRepository pointSystemRepository;

    public PointSystemService(PointSystemRepository pointSystemRepository) {
        super(pointSystemRepository);
        this.pointSystemRepository = pointSystemRepository;
    }

    public Optional<PointSystem> getPointSystemByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Point system name cannot be null or empty");
        }
        return pointSystemRepository.findByName(name);
    }

    public Optional<PointSystem> getPointSystemByDisplayName(String displayName) {
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Point system display name cannot be null or empty");
        }
        return pointSystemRepository.findByDisplayName(displayName);
    }

    public boolean isActive() {
        return pointSystemRepository.isActive();
    }

    public boolean existsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Point system name cannot be null or empty");
        }
        return pointSystemRepository.existsByName(name);
    }

    @Override
    protected boolean isDuplicate(Long id, @NotNull PointSystem pointSystem) {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(pointSystem, "Point system details cannot be null");

        PointSystem existingPointSystem = getById(id)
        .orElseThrow(() -> new ResourceNotFoundException("PointSystem", "id", id));

        return existingPointSystem.getName().equals(pointSystem.getName());
    }
}
