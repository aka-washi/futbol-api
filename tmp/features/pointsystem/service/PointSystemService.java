package com.eagle.futbolapi.features.pointsystem.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.pointsystem.entity.PointSystem;
import com.eagle.futbolapi.features.pointsystem.repository.PointSystemRepository;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.shared.service.BaseCrudService;

@Service
@Transactional
public class PointSystemService extends BaseCrudService<PointSystem, Long> {

    private static final String CANNOT_BE_NULL = "Point System cannot be null";

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

    public boolean existsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Point system name cannot be null or empty");
        }
        return pointSystemRepository.existsByName(name);
    }

    public boolean existsByPointValues(PointSystem pointSystem) {
        Objects.requireNonNull(pointSystem, CANNOT_BE_NULL);

        return pointSystemRepository.existsByPointValues(
            pointSystem.getPointsForWin(),
            pointSystem.getPointsForDraw(),
            pointSystem.getPointsForLoss(),
            pointSystem.getPointsForWinOnPenalties(),
            pointSystem.getPointsForLossOnPenalties()
        );
    }

    public Optional<PointSystem> findByPointValues(PointSystem pointSystem) {
        Objects.requireNonNull(pointSystem, CANNOT_BE_NULL);

        return pointSystemRepository.findByPointValues(
            pointSystem.getPointsForWin(),
            pointSystem.getPointsForDraw(),
            pointSystem.getPointsForLoss(),
            pointSystem.getPointsForWinOnPenalties(),
            pointSystem.getPointsForLossOnPenalties()
        );
    }

    @Override
    protected boolean isDuplicate(PointSystem pointSystem) {
        Objects.requireNonNull(pointSystem, CANNOT_BE_NULL);

        // Check if a PointSystem with the same point values already exists
        return existsByPointValues(pointSystem);
    }

    @Override
    protected boolean isDuplicate(Long id, @NotNull PointSystem pointSystem) {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(pointSystem, "Point system details cannot be null");

        // Get the existing entity to compare values
        PointSystem existingPointSystem = getById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Point System", "id", id));

        // Check if any point values are being changed
        boolean pointValuesChanged = !Objects.equals(existingPointSystem.getPointsForWin(), pointSystem.getPointsForWin()) ||
                                   !Objects.equals(existingPointSystem.getPointsForDraw(), pointSystem.getPointsForDraw()) ||
                                   !Objects.equals(existingPointSystem.getPointsForLoss(), pointSystem.getPointsForLoss()) ||
                                   !Objects.equals(existingPointSystem.getPointsForWinOnPenalties(), pointSystem.getPointsForWinOnPenalties()) ||
                                   !Objects.equals(existingPointSystem.getPointsForLossOnPenalties(), pointSystem.getPointsForLossOnPenalties());

        // If point values are being changed, check if the new point values already exist in another entity
        if (pointValuesChanged) {
            Optional<PointSystem> duplicatePointSystem = findByPointValues(pointSystem);
            return duplicatePointSystem.isPresent() && !duplicatePointSystem.get().getId().equals(id);
        }

        // If only non-point fields are being changed, check for name duplicates
        boolean nameChanged = !Objects.equals(existingPointSystem.getName(), pointSystem.getName());
        if (nameChanged) {
            return pointSystemRepository.existsByName(pointSystem.getName());
        }

        // No relevant duplicates found
        return false;
    }
}
