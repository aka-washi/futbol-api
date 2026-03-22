package com.eagle.futbolapi.features.season.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.season.dto.SeasonDto;
import com.eagle.futbolapi.features.season.entity.Season;
import com.eagle.futbolapi.features.season.mapper.SeasonMapper;
import com.eagle.futbolapi.features.season.repository.SeasonRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service for Season entity business logic.
 */
@Slf4j
@Service
@Transactional
@Validated
public class SeasonService extends BaseCrudService<Season, Long, SeasonDto> {

  protected SeasonService(SeasonRepository repository, SeasonMapper mapper) {
    super(repository, mapper);
  }

  public Optional<Season> findByDisplayName(String displayName) {
    if (displayName == null || displayName.isEmpty()) {
      throw new IllegalArgumentException("Season display name cannot be null or empty");
    }
    return ((SeasonRepository) repository).findByDisplayName(displayName);
  }

  @Override
  protected boolean isDuplicate(@NotNull Season season) {
    Objects.requireNonNull(season, "Season cannot be null");

    return isDuplicate(season, UniquenessStrategy.ANY);
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Season season) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(season, "Season cannot be null");

    log.debug(
        "Checking for duplicates: ID={}, name={}, displayName={}",
        id, season.getName(), season.getDisplayName());

    boolean result = isDuplicate(id, season, UniquenessStrategy.ANY);
    log.debug("Duplicate check result for ID {}: {}", id, result);
    return result;
  }

}
