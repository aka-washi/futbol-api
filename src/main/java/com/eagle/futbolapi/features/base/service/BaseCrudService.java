package com.eagle.futbolapi.features.base.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

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

  // ============================================================================
  // Standard CRUD Operations
  // ============================================================================

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

  /**
   * Partially update an entity with only the non-null fields from the DTO.
   * This method uses reflection to copy only the non-null fields from the DTO to
   * the existing entity.
   *
   * @param id  the ID of the entity to update
   * @param dto the DTO containing the fields to update (null fields are ignored)
   * @return the updated entity
   */
  public T patch(K id, D dto) {
    // Get existing entity
    T existing = getById(id).orElseThrow(
        () -> new IllegalArgumentException("Entity with given ID does not exist"));

    // Apply partial updates using reflection
    applyPartialUpdate(dto, existing);

    // Resolve relationships for any updated relationship fields
    resolveRelationships(dto, existing);

    // Check for duplicates
    if (isDuplicate(id, existing)) {
      throw new IllegalArgumentException("Duplicate entity");
    }

    return repository.save(existing);
  }

  /**
   * Apply partial update from DTO to entity using reflection.
   * Only non-null fields from the DTO are copied to the entity.
   *
   * @param dto    the source DTO
   * @param entity the target entity
   */
  protected void applyPartialUpdate(D dto, T entity) {
    if (dto == null || entity == null) {
      return;
    }

    Class<?> dtoClass = dto.getClass();
    Class<?> entityClass = entity.getClass();

    for (Field dtoField : getAllFields(dtoClass)) {
      try {
        dtoField.setAccessible(true);
        Object value = dtoField.get(dto);

        // Skip null values and ID field (should not be updated)
        if (value == null || "id".equals(dtoField.getName())) {
          continue;
        }

        // Skip audit fields
        if (isAuditField(dtoField.getName())) {
          continue;
        }

        // Skip relationship ID fields (they will be handled by resolveRelationships)
        if (dtoField.getName().endsWith("Id") && !dtoField.getName().equals("id")) {
          continue;
        }

        // Try to find matching field in entity
        Field entityField = findField(entityClass, dtoField.getName());
        if (entityField != null) {
          entityField.setAccessible(true);
          entityField.set(entity, value);
        }
      } catch (IllegalAccessException e) {
        // Skip fields that cannot be accessed
      }
    }
  }

  /**
   * Get all fields from a class and its superclasses.
   */
  private List<Field> getAllFields(Class<?> clazz) {
    List<Field> fields = new ArrayList<>();
    while (clazz != null && clazz != Object.class) {
      for (Field field : clazz.getDeclaredFields()) {
        fields.add(field);
      }
      clazz = clazz.getSuperclass();
    }
    return fields;
  }

  /**
   * Find a field by name in a class or its superclasses.
   */
  private Field findField(Class<?> clazz, String fieldName) {
    while (clazz != null && clazz != Object.class) {
      try {
        return clazz.getDeclaredField(fieldName);
      } catch (NoSuchFieldException e) {
        clazz = clazz.getSuperclass();
      }
    }
    return null;
  }

  /**
   * Check if a field is an audit field that should not be updated.
   */
  private boolean isAuditField(String fieldName) {
    return "createdAt".equals(fieldName) ||
        "createdBy".equals(fieldName) ||
        "updatedAt".equals(fieldName) ||
        "updatedBy".equals(fieldName);
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

  // ============================================================================
  // Generic Unique Fields Methods using JPA Specifications
  // ============================================================================

  /**
   * Build a JPA Specification from a map of unique field values.
   * Supports nested fields using dot notation (e.g., "season.id", "team.id").
   *
   * @param uniqueFields map of field names to values
   * @return Specification for querying
   */
  protected Specification<T> buildUniqueFieldsSpec(Map<String, Object> uniqueFields) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();
      uniqueFields.forEach((field, value) -> {
        if (value != null) {
          Path<?> path = getPath(root, field);
          predicates.add(cb.equal(path, value));
        }
      });
      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }

  /**
   * Get the path for a field, supporting nested fields with dot notation.
   */
  @SuppressWarnings("unchecked")
  private <Y> Path<Y> getPath(Path<?> root, String field) {
    if (field.contains(".")) {
      String[] parts = field.split("\\.");
      Path<?> path = root;
      for (String part : parts) {
        path = path.get(part);
      }
      return (Path<Y>) path;
    }
    return (Path<Y>) root.get(field);
  }

  /**
   * Find entity by unique fields using dynamic specification.
   *
   * @param uniqueFields map of field names to values (supports dot notation for
   *                     nested fields)
   * @return Optional containing the entity if found
   */
  public Optional<T> getByUniqueFields(Map<String, Object> uniqueFields) {
    if (uniqueFields == null || uniqueFields.isEmpty()) {
      return Optional.empty();
    }
    return getSpecificationExecutor().findOne(buildUniqueFieldsSpec(uniqueFields));
  }

  /**
   * Check if entity exists by unique fields.
   *
   * @param uniqueFields map of field names to values
   * @return true if entity exists
   */
  public boolean existsByUniqueFields(Map<String, Object> uniqueFields) {
    if (uniqueFields == null || uniqueFields.isEmpty()) {
      return false;
    }
    return getSpecificationExecutor().exists(buildUniqueFieldsSpec(uniqueFields));
  }

  /**
   * Check if entity exists by unique fields excluding given ID.
   *
   * @param uniqueFields map of field names to values
   * @param id           ID to exclude from the check
   * @return true if another entity exists with the same unique fields
   */
  public boolean existsByUniqueFieldsAndNotId(Map<String, Object> uniqueFields, K id) {
    if (uniqueFields == null || uniqueFields.isEmpty() || id == null) {
      return false;
    }
    Specification<T> spec = buildUniqueFieldsSpec(uniqueFields)
        .and((root, query, cb) -> cb.notEqual(root.get("id"), id));
    return getSpecificationExecutor().exists(spec);
  }

  /**
   * Get the JpaSpecificationExecutor from the repository.
   * Throws exception if repository doesn't support specifications.
   */
  @SuppressWarnings("unchecked")
  protected JpaSpecificationExecutor<T> getSpecificationExecutor() {
    if (repository instanceof JpaSpecificationExecutor) {
      return (JpaSpecificationExecutor<T>) repository;
    }
    throw new UnsupportedOperationException(
        "Repository does not support JpaSpecificationExecutor. " +
            "Extend BaseRepository instead of JpaRepository.");
  }

  // ============================================================================
  // Helper Methods
  // ============================================================================

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
