package com.eagle.futbolapi.features.pointsystem.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.pointsystem.dto.PointSystemDto;
import com.eagle.futbolapi.features.pointsystem.entity.PointSystem;
import com.eagle.futbolapi.features.pointsystem.mapper.PointSystemMapper;
import com.eagle.futbolapi.features.pointsystem.repository.PointSystemRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service for PointSystem entity business logic.
 */
@Slf4j
@Service
@Transactional
public class PointSystemService extends BaseCrudService<PointSystem, Long, PointSystemDto> {

  protected PointSystemService(PointSystemRepository repository, PointSystemMapper mapper) {
    super(repository, mapper);
  }

  public Optional<PointSystem> findByDisplayName(String displayName) {
    if (displayName == null || displayName.isEmpty()) {
      throw new IllegalArgumentException("PointSystem display name cannot be null or empty");
    }
    return ((PointSystemRepository) repository).findByDisplayName(displayName);
  }

  @Override
  protected boolean isDuplicate(@NotNull PointSystem pointSystem) {
    Objects.requireNonNull(pointSystem, "PointSystem cannot be null");

    return isDuplicate(pointSystem, UniquenessStrategy.ALL);
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull PointSystem pointSystem) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(pointSystem, "PointSystem cannot be null");

    log.debug(
        "Checking for duplicates: ID={}, name={}, displayName={}, pointsForWin={}, pointsForDraw={}, pointsForLoss={}, pointsForWinOnPenalties={}, pointsForLossOnPenalties={}, pointsForForfeitWin={}, pointsForForfeitLoss={}",
        id, pointSystem.getName(), pointSystem.getDisplayName(),
        pointSystem.getPointsForWin(), pointSystem.getPointsForDraw(), pointSystem.getPointsForLoss(),
        pointSystem.getPointsForWinOnPenalties(), pointSystem.getPointsForLossOnPenalties(),
        pointSystem.getPointsForForfeitWin(), pointSystem.getPointsForForfeitLoss());

    boolean result = isDuplicate(id, pointSystem, UniquenessStrategy.ALL);
    log.debug("Duplicate check result for ID {}: {}", id, result);
    return result;
  }
}
