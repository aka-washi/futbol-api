package com.eagle.futbolapi.features.pointsystem.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.exception.NoChangesDetectedException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.pointsystem.dto.PointSystemDTO;
import com.eagle.futbolapi.features.pointsystem.entity.PointSystem;
import com.eagle.futbolapi.features.pointsystem.mapper.PointSystemMapper;
import com.eagle.futbolapi.features.pointsystem.repository.PointSystemRepository;

@Service
@Transactional
public class PointSystemService extends BaseCrudService<PointSystem, Long, PointSystemDTO> {

  private final PointSystemRepository pointSystemRepository;
  private final PointSystemMapper pointSystemMapper;

  public PointSystemService(PointSystemRepository repository, PointSystemMapper pointSystemMapper) {
    super(repository);
    this.pointSystemRepository = repository;
    this.pointSystemMapper = pointSystemMapper;
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

  @Override
  protected PointSystem convertToEntity(PointSystemDTO dto) {
    return pointSystemMapper.toPointSystem(dto);
  }

  @Override
  protected boolean isDuplicate(@NotNull PointSystem pointSystem) {
    Objects.requireNonNull(pointSystem, "Point System cannot be null");

    return pointSystemRepository.existsByPointValues(
        pointSystem.getPointsForWin(),
        pointSystem.getPointsForDraw(),
        pointSystem.getPointsForLoss(),
        pointSystem.getPointsForWinOnPenalties(),
        pointSystem.getPointsForLossOnPenalties());
  }

  @Override
  protected boolean isDuplicate(Long id, @NotNull PointSystem pointSystem) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(pointSystem, "Point System details cannot be null");

    return pointSystemRepository.existsByPointValuesAndIdNot(
        pointSystem.getPointsForWin(),
        pointSystem.getPointsForDraw(),
        pointSystem.getPointsForLoss(),
        pointSystem.getPointsForWinOnPenalties(),
        pointSystem.getPointsForLossOnPenalties(),
        id);
  }

}
