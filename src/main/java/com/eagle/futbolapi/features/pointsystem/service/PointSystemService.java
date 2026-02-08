package com.eagle.futbolapi.features.pointsystem.service;

import java.util.Objects;

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
 * Service class for managing Point System entities.
 * Provides CRUD operations and business logic specific to point systems,
 * including duplicate checking based on uniqueness constraints.
 *
 * <p>
 * This service ensures that point systems are unique based on the combination
 * of all their point allocation values using the ALL strategy for duplicate
 * detection.
 */
@Slf4j
@Service
@Transactional
public class PointSystemService extends BaseCrudService<PointSystem, Long, PointSystemDto> {

  /**
   * Constructs a new PointSystemService with the required dependencies.
   *
   * @param repository the PointSystemRepository for data access
   * @param mapper     the PointSystemMapper for entity-DTO conversion
   */
  protected PointSystemService(PointSystemRepository repository, PointSystemMapper mapper) {
    super(repository, mapper);
  }

  /**
   * Checks if a point system entity is a duplicate based on uniqueness
   * constraints.
   * Uses ALL strategy to check against all unique fields.
   *
   * @param pointSystem the point system entity to check for duplicates
   * @return true if the point system is a duplicate, false otherwise
   * @throws NullPointerException if pointSystem is null
   */
  @Override
  protected boolean isDuplicate(@NotNull PointSystem pointSystem) {
    Objects.requireNonNull(pointSystem, "PointSystem cannot be null");

    return isDuplicate(pointSystem, UniquenessStrategy.ALL);
  }

  /**
   * Checks if a point system entity is a duplicate, excluding the entity with the
   * given ID.
   * Uses ALL strategy to check against all unique fields.
   *
   * @param id          the ID of the entity to exclude from duplicate check
   * @param pointSystem the point system entity to check for duplicates
   * @return true if the point system is a duplicate, false otherwise
   * @throws NullPointerException if id or pointSystem is null
   */
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
