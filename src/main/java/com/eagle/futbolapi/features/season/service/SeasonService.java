package com.eagle.futbolapi.features.season.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.season.dto.SeasonDto;
import com.eagle.futbolapi.features.season.entity.Season;
import com.eagle.futbolapi.features.season.mapper.SeasonMapper;
import com.eagle.futbolapi.features.season.repository.SeasonRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service class for managing Season entities.
 * Provides CRUD operations and business logic specific to seasons,
 * including duplicate checking based on uniqueness constraints.
 *
 * <p>
 * This service ensures that season names and display names are unique
 * across the system using the ANY strategy for duplicate detection.
 */
@Slf4j
@Service
@Transactional
public class SeasonService extends BaseCrudService<Season, Long, SeasonDto> {

  /**
   * Constructs a new SeasonService with the required dependencies.
   *
   * @param repository the SeasonRepository for data access
   * @param mapper     the SeasonMapper for entity-DTO conversion
   */
  protected SeasonService(SeasonRepository repository, SeasonMapper mapper) {
    super(repository, mapper);
  }

  /**
   * Checks if a season entity is a duplicate based on uniqueness constraints.
   * Uses ANY strategy to check against all unique fields.
   *
   * @param season the season entity to check for duplicates
   * @return true if the season is a duplicate, false otherwise
   * @throws NullPointerException if season is null
   */
  @Override
  protected boolean isDuplicate(@NotNull Season season) {
    Objects.requireNonNull(season, "Season cannot be null");

    return isDuplicate(season, UniquenessStrategy.ANY);
  }

  /**
   * Checks if a season entity is a duplicate, excluding the entity with the
   * given ID.
   * Uses ANY strategy to check against all unique fields.
   *
   * @param id     the ID of the entity to exclude from duplicate check
   * @param season the season entity to check for duplicates
   * @return true if the season is a duplicate, false otherwise
   * @throws NullPointerException if id or season is null
   */
  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Season season) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(season, "Season cannot be null");

    return isDuplicate(id, season, UniquenessStrategy.ANY);
  }

}
