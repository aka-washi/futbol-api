package com.eagle.futbolapi.features.base.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.base.exception.NoChangesDetectedException;

public abstract class BaseCrudService<T extends BaseEntity, K, D> {
  protected final JpaRepository<T, K> repository;

  protected BaseCrudService(JpaRepository<T, K> repository) {
    this.repository = repository;
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

  /**
   * Creates an entity from a DTO with relationship resolution.
   */
  public T create(D dto) {
    T entity = convertToEntity(dto);
    resolveRelationships(dto, entity);
    return saveNew(entity);
  }

  /**
   * Internal method to save a new entity.
   */
  private T saveNew(T entity) {
    if (entity == null) {
      throw new IllegalArgumentException("Entity cannot be null");
    }

    if (isDuplicate(entity)) {
      throw new IllegalArgumentException("Duplicate entity");
    }
    return repository.save(entity);
  }

  /**
   * Updates an entity from a DTO with relationship resolution.
   */
  public T update(K id, D dto) {
    T entity = convertToEntity(dto);
    resolveRelationships(dto, entity);
    return saveExisting(id, entity);
  }

  /**
   * Internal method to save an existing entity.
   */
  private T saveExisting(K id, T entity) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(entity, "Entity details cannot be null");

    // Check if entity exists
    if (!repository.existsById(id)) {
      throw new IllegalArgumentException("Entity with given ID does not exist");
    }

    // Get the existing entity from database
    T existingEntity = repository.findById(id).orElseThrow(
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

  protected abstract boolean isDuplicate(@NotNull T entity);

  protected abstract boolean isDuplicate(K id, @NotNull T entity);

  /**
   * Converts DTO to entity. Subclasses must implement this using their mapper.
   */
  protected abstract T convertToEntity(D dto);

  /**
   * Resolves entity relationships from DTO (e.g., looking up foreign keys by display names).
   * Default implementation does nothing. Override in subclasses that need relationship resolution.
   */
  protected void resolveRelationships(D dto, T entity) {
    // Default: no relationships to resolve
  }

}
