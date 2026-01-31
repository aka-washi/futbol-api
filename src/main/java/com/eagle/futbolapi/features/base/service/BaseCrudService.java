package com.eagle.futbolapi.features.base.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.eagle.futbolapi.features.base.annotation.UniqueField;
import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
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
   * Get the value of a nested field using dot notation (e.g., "tournament.id").
   *
   * @param obj       the object to start from
   * @param fieldPath the dot-separated field path
   * @return the value of the nested field
   * @throws NoSuchFieldException   if any field in the path doesn't exist
   * @throws IllegalAccessException if any field cannot be accessed
   */
  private Object getNestedFieldValue(Object obj, String fieldPath) throws NoSuchFieldException, IllegalAccessException {
    if (obj == null || fieldPath == null || fieldPath.isEmpty()) {
      return null;
    }

    String[] parts = fieldPath.split("\\.");
    Object current = obj;

    for (String part : parts) {
      if (current == null) {
        return null;
      }

      Field field = findField(current.getClass(), part);
      if (field == null) {
        throw new NoSuchFieldException("Field '" + part + "' not found in " + current.getClass().getSimpleName());
      }

      field.setAccessible(true);
      current = field.get(current);
    }

    return current;
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
   * Build a unique fields map for an entity by extracting all fields annotated
   * with @UniqueField.
   * This utility method uses reflection to automatically identify unique fields
   * and their values.
   * Supports nested field access using dot notation in the fieldPath attribute.
   *
   * @param entity the entity to extract unique fields from
   * @return Map of field paths to values for fields marked with @UniqueField
   */
  protected Map<String, Object> buildUniqueFieldsMap(T entity) {
    Map<String, Object> uniqueFields = new HashMap<>();
    if (entity == null) {
      return uniqueFields;
    }

    Class<?> entityClass = entity.getClass();
    for (Field field : getAllFields(entityClass)) {
      // Check if field has @UniqueField annotation
      if (field.isAnnotationPresent(UniqueField.class)) {
        UniqueField uniqueFieldAnnotation = field.getAnnotation(UniqueField.class);
        String fieldPath = uniqueFieldAnnotation.fieldPath();

        // If fieldPath is empty, use the field name
        if (fieldPath.isEmpty()) {
          fieldPath = field.getName();
        }

        try {
          field.setAccessible(true);
          Object value = field.get(entity);

          // If the fieldPath contains dots, we need to resolve nested access
          if (fieldPath.contains(".")) {
            value = getNestedFieldValue(entity, fieldPath);
          }

          if (value != null) {
            uniqueFields.put(fieldPath, value);
          }
        } catch (IllegalAccessException | NoSuchFieldException e) {
          // Skip fields that cannot be accessed or don't exist
        }
      }
    }
    return uniqueFields;
  }

  /**
   * Build a unique fields map for an entity by combining automatically detected
   * unique fields
   * with additional manually specified unique field mappings.
   * This is useful for composite unique constraints or unique fields not marked
   * with @Column(unique = true).
   *
   * @param entity                 the entity to extract unique fields from
   * @param additionalUniqueFields additional field mappings to include (e.g.,
   *                               Map.of("tournament.id", tournamentId))
   * @return Map of field names to values combining both automatic detection and
   *         manual specification
   */
  protected Map<String, Object> buildUniqueFieldsMap(T entity, Map<String, Object> additionalUniqueFields) {
    Map<String, Object> uniqueFields = buildUniqueFieldsMap(entity);
    if (additionalUniqueFields != null) {
      uniqueFields.putAll(additionalUniqueFields);
    }
    return uniqueFields;
  }

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

  // ============================================================================
  // Uniqueness Checking Helper Methods
  // ============================================================================

  /**
   * Check if an entity is a duplicate using the specified uniqueness strategy.
   *
   * @param entity   the entity to check
   * @param strategy the uniqueness strategy (ALL or ANY)
   * @return true if the entity is considered a duplicate
   */
  protected boolean isDuplicate(@NotNull T entity, @NotNull UniquenessStrategy strategy) {
    Objects.requireNonNull(entity, "Entity cannot be null");
    Objects.requireNonNull(strategy, "Uniqueness strategy cannot be null");

    Map<String, Object> uniqueFields = buildUniqueFieldsMap(entity);
    if (uniqueFields.isEmpty()) {
      return false;
    }

    return switch (strategy) {
      case ALL -> existsByUniqueFields(uniqueFields);
      case ANY -> uniqueFields.entrySet().stream()
          .anyMatch(entry -> existsByUniqueFields(Map.of(entry.getKey(), entry.getValue())));
    };
  }

  /**
   * Check if an entity is a duplicate (excluding given ID) using the specified
   * uniqueness strategy.
   *
   * @param id       the ID to exclude from the check
   * @param entity   the entity to check
   * @param strategy the uniqueness strategy (ALL or ANY)
   * @return true if another entity exists with the same unique fields
   */
  protected boolean isDuplicate(@NotNull K id, @NotNull T entity, @NotNull UniquenessStrategy strategy) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(entity, "Entity cannot be null");
    Objects.requireNonNull(strategy, "Uniqueness strategy cannot be null");

    Map<String, Object> uniqueFields = buildUniqueFieldsMap(entity);
    if (uniqueFields.isEmpty()) {
      return false;
    }

    return switch (strategy) {
      case ALL -> existsByUniqueFieldsAndNotId(uniqueFields, id);
      case ANY -> uniqueFields.entrySet().stream()
          .anyMatch(entry -> existsByUniqueFieldsAndNotId(Map.of(entry.getKey(), entry.getValue()), id));
    };
  }

  protected abstract boolean isDuplicate(@NotNull T entity);

  protected abstract boolean isDuplicate(@NotNull K id, @NotNull T entity);

}
