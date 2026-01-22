package com.eagle.futbolapi.features.base.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.base.exception.NoChangesDetectedException;
import com.eagle.futbolapi.features.base.mapper.BaseMapper;

public abstract class BaseCrudService<T extends BaseEntity, K, D> {
  protected final JpaRepository<T, K> repository;
  protected final BaseMapper<T, D> mapper;

  protected BaseCrudService(JpaRepository<T, K> repository, BaseMapper<T, D> mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  public Page<T> getAll(Pageable pageable) {
    if (pageable == null) {
      pageable = Pageable.unpaged();
    }
    return repository.findAll(pageable);
  }

  public Optional<T> getById(K id) {
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null");
    }
    return repository.findById(id);
  }

  public T create(D dto) {
    T entity = convertToEntity(dto);
    resolveRelationships(dto, entity);
    return saveNew(entity);
  }

  public T update(K id, D dto) {
    // Get existing season to preserve audit fields
    T existing = getById(id).orElseThrow(
        () -> new IllegalArgumentException("Entity with given ID does not exist"));

    // Convert DTO to entity and resolve relationships
    T entity = convertToEntity(dto);
    resolveRelationships(dto, entity);

    // Preserve audit fields from existing season
    entity.setId((Long) id);
    entity.setCreatedAt(existing.getCreatedAt());
    entity.setCreatedBy(existing.getCreatedBy());

    // Validate and save
    if (Objects.equals(existing, entity)) {
      throw new NoChangesDetectedException("No changes detected for entity", id);
    }
    if (isDuplicate(id, entity)) {
      throw new IllegalArgumentException("Duplicate entity");
    }

    return saveExisting(id, entity);
  }

  public void delete(K id) {
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null");
    }
    repository.deleteById(id);
  }

  public boolean existsById(K id) {
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null");
    }
    return repository.existsById(id);
  }

  private T saveNew(T entity) {
    if (entity == null) {
      throw new IllegalArgumentException("Entity cannot be null");
    }

    if (isDuplicate(entity)) {
      throw new IllegalArgumentException("Duplicate entity");
    }
    return repository.save(entity);
  }

  private T saveExisting(K id, T entity) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(entity, "Entity details cannot be null");

    // Check if entity exists
    if (!repository.existsById(id)) {
      throw new IllegalArgumentException("Entity with given ID does not exist");
    }

    // Get the existing entity from database
    T existingEntity = getById(id).orElseThrow(
        () -> new IllegalArgumentException("Entity with given ID does not exist"));

    // Set the ID to ensure we're updating the right entity
    entity.setId((Long) id);

    // Check if there are any actual changes by comparing entities
    // Entity's equals method should compare business fields only (excluding audit
    // fields)
    if (existingEntity.equals(entity)) {
      throw new NoChangesDetectedException("No changes detected for entity", id);
    }

    // Check for duplicates
    if (isDuplicate(id, entity)) {
      throw new IllegalArgumentException("Duplicate entity");
    }

    return repository.save(entity);
  }

  protected T convertToEntity(@NotNull D dto) {
    return mapper.toEntity(dto);
  }

  protected void resolveRelationships(D dto, T entity) {
    // Default: no relationships to resolve
  }

  protected abstract boolean isDuplicate(@NotNull T entity);

  protected abstract boolean isDuplicate(@NotNull K id, @NotNull T entity);

}
