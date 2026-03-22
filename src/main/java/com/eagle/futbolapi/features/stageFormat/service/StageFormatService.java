package com.eagle.futbolapi.features.stageFormat.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.pointsystem.service.PointSystemService;
import com.eagle.futbolapi.features.stageFormat.dto.StageFormatDto;
import com.eagle.futbolapi.features.stageFormat.entity.StageFormat;
import com.eagle.futbolapi.features.stageFormat.mapper.StageFormatMapper;
import com.eagle.futbolapi.features.stageFormat.repository.StageFormatRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service for StageFormat entity business logic.
 */
@Slf4j
@Service
@Transactional
@Validated
public class StageFormatService extends BaseCrudService<StageFormat, Long, StageFormatDto> {

  private final PointSystemService pointSystemService;

  protected StageFormatService(StageFormatRepository repository, StageFormatMapper mapper,
      PointSystemService pointSystemService) {
    super(repository, mapper);
    this.pointSystemService = pointSystemService;
  }

  public Optional<StageFormat> findByDisplayName(String displayName) {
    if (displayName == null || displayName.isEmpty()) {
      throw new IllegalArgumentException("StageFormat display name cannot be null or empty");
    }
    return ((StageFormatRepository) repository).findByDisplayName(displayName);
  }

  @Override
  protected boolean isDuplicate(@NotNull StageFormat stageFormat) {
    Objects.requireNonNull(stageFormat, "StageFormat cannot be null");
    return isDuplicate(stageFormat, UniquenessStrategy.ANY);
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull StageFormat stageFormat) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(stageFormat, "StageFormat cannot be null");

    log.debug(
        "Checking for duplicates: ID={}, displayName={}, type={}",
        id, stageFormat.getDisplayName(), stageFormat.getType());

    boolean result = isDuplicate(id, stageFormat, UniquenessStrategy.ANY);
    log.debug("Duplicate check result for ID {}: {}", id, result);
    return result;
  }

  @Override
  protected void resolveRelationships(StageFormatDto dto, StageFormat stageFormat) {
    // Map point system from display name or ID (optional relationship)
    if (dto.getPointSystemDisplayName() != null && !dto.getPointSystemDisplayName().trim().isEmpty()) {
      var pointSystem = pointSystemService.findByDisplayName(dto.getPointSystemDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("PointSystem", "displayName",
              dto.getPointSystemDisplayName()));
      stageFormat.setPointSystem(pointSystem);
    } else if (dto.getPointSystemId() != null) {
      var pointSystem = pointSystemService.getById(dto.getPointSystemId())
          .orElseThrow(() -> new ResourceNotFoundException("PointSystem", "id", dto.getPointSystemId()));
      stageFormat.setPointSystem(pointSystem);
    }
  }
}
