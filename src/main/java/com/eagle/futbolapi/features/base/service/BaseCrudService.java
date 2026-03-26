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

import com.eagle.futbolapi.features.base.annotation.GeneratedField;
import com.eagle.futbolapi.features.base.annotation.UniqueField;
import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.exception.NoChangesDetectedException;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.mapper.BaseMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
    log.debug("Fetching all entities with pageable: {}", pageable);
    if (pageable == null) {
      pageable = Pageable.unpaged();
    }
    Page<T> result = repository.findAll(pageable);
    log.debug("Retrieved {} entities", result.getNumberOfElements());
    return result;
  }

  public Optional<T> getById(K id) {
    log.debug("Fetching entity by id: {}", id);
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null");
    }
    Optional<T> result = repository.findById(id);
    if (result.isPresent()) {
      log.debug("Found entity with id: {}", id);
    } else {
      log.debug("No entity found with id: {}", id);
    }
    return result;
  }

  public T create(D dto) {
    log.info("Creating new entity from DTO");
    T entity = convertToEntity(dto);
    // Ensure new entities are persisted, not merged. Clear any incoming ID to avoid
    // attempting to merge a detached entity which can cause optimistic locking
    // failures if the provided ID refers to a row that was updated/deleted.
    try {
      entity.setId(null);
    } catch (Exception e) {
      log.debug("Could not clear ID on new entity: {}", e.getMessage());
    }
    resolveRelationships(dto, entity);
    // Allow entity-specific business validations before persisting
    validateForCreate(dto, entity);
    T saved = saveNew(entity);
    log.info("Successfully created entity with id: {}", saved.getId());
    return saved;
  }

  public T update(K id, D dto) {
    log.info("Updating entity with id: {}", id);
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

    // Preserve generated fields (e.g., auto-generated keys)
    preserveGeneratedFields(existing, entity);

    // Validate and save
    if (Objects.equals(existing, entity)) {
      log.warn("No changes detected for entity with id: {}", id);
      throw new NoChangesDetectedException("No changes detected for entity", id);
    }
    if (isDuplicate(id, entity)) {
      log.error("Duplicate entity detected for id: {}", id);
      throw new IllegalArgumentException("Duplicate entity");
    }

    T updated = saveExisting(id, entity);
    log.info("Successfully updated entity with id: {}", id);
    return updated;
  }

  public T patch(K id, D dto) {
    log.debug("Patching entity with ID: {}", id);

    // Get existing entity
    T existing = getById(id).orElseThrow(
        () -> new IllegalArgumentException("Entity with given ID does not exist"));

    log.debug("Existing entity before patch: {}", existing);

    // Apply partial updates using reflection
    applyPartialUpdate(dto, existing);

    log.debug("Entity after partial update: {}", existing);

    // Resolve relationships for any updated relationship fields
    resolveRelationships(dto, existing);

    // Allow entity-specific business validations before saving updates
    validateForPatch(dto, existing);

    log.debug("Entity after relationship resolution: {}", existing);

    // Check for duplicates
    if (isDuplicate(id, existing)) {
      log.error("Duplicate entity detected for ID: {}", id);
      throw new IllegalArgumentException("Duplicate entity");
    }

    log.debug("No duplicates found, proceeding to save");

    try {
      T saved = repository.save(existing);
      log.debug("Successfully saved entity with ID: {}", id);
      return saved;
    } catch (Exception e) {
      // Get the root cause
      Throwable rootCause = e;
      while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
        rootCause = rootCause.getCause();
      }
      log.error("Failed to save entity during patch operation for ID: {}. Error: {} - Root cause: {}",
          id, e.getMessage(), rootCause.getMessage(), rootCause);
      throw e;
    }
  }

  protected void applyPartialUpdate(D dto, T entity) {
    if (dto == null || entity == null) {
      return;
    }

    Class<?> dtoClass = dto.getClass();
    Class<?> entityClass = entity.getClass();

    log.debug("Applying partial update from DTO to entity");

    for (Field dtoField : getAllFields(dtoClass)) {
      try {
        dtoField.setAccessible(true);
        Object value = dtoField.get(dto);

        // Skip ID field (should not be updated)
        if ("id".equals(dtoField.getName())) {
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
        if (entityField == null) {
          continue;
        }

        // Skip fields marked as generated (auto-generated and cannot be modified)
        if (entityField.isAnnotationPresent(GeneratedField.class)) {
          if (value != null) {
            GeneratedField annotation = entityField.getAnnotation(GeneratedField.class);
            log.warn("Attempted to modify generated field '{}': {}. {}",
                dtoField.getName(), value, annotation.message());
          }
          continue;
        }

        // Handle null values: skip all null values in PATCH operations
        // (null in DTO means field was not provided, not an explicit null)
        if (value == null) {
          log.debug("Skipping null value for field: {}", dtoField.getName());
          continue;
        }

        entityField.setAccessible(true);
        Object oldValue = entityField.get(entity);
        entityField.set(entity, value);
        log.debug("Updated field '{}': {} -> {}", dtoField.getName(), oldValue, value);
      } catch (IllegalAccessException e) {
        log.warn("Could not access field: {}", dtoField.getName());
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

  /**
   * Preserve generated fields from the existing entity to the new entity.
   * Fields marked with @GeneratedField cannot be modified and must be preserved.
   */
  private void preserveGeneratedFields(T existing, T entity) {
    Class<?> entityClass = entity.getClass();

    // Traverse the class hierarchy to find all fields
    while (entityClass != null && !entityClass.equals(Object.class)) {
      Field[] fields = entityClass.getDeclaredFields();

      for (Field field : fields) {
        if (field.isAnnotationPresent(GeneratedField.class)) {
          try {
            field.setAccessible(true);
            Object existingValue = field.get(existing);
            field.set(entity, existingValue);
            log.debug("Preserved generated field '{}': {}", field.getName(), existingValue);
          } catch (IllegalAccessException e) {
            log.warn("Could not preserve generated field: {}", field.getName());
          }
        }
      }

      entityClass = entityClass.getSuperclass();
    }
  }

  public void delete(K id) {
    log.info("Deleting entity with id: {}", id);
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null");
    }
    if (!existsById(id)) {
      log.error("Failed to delete: Resource not found with id: {}", id);
      throw new ResourceNotFoundException("Resource not found with ID: " + id);
    }
    repository.deleteById(id);
    log.info("Successfully deleted entity with id: {}", id);
  }

  public boolean existsById(K id) {
    log.debug("Checking existence of entity with id: {}", id);
    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null");
    }
    boolean exists = repository.existsById(id);
    log.debug("Entity with id {} exists: {}", id, exists);
    return exists;
  }

  // ============================================================================
  // Generic Unique Fields Methods using JPA Specifications
  // ============================================================================

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

          // Include all fields, even null values, for proper uniqueness checking
          uniqueFields.put(fieldPath, value);
          log.debug("Added unique field '{}' with value: {}", fieldPath, value);
        } catch (IllegalAccessException | NoSuchFieldException e) {
          log.warn("Could not access unique field '{}': {}", fieldPath, e.getMessage());
          // Skip fields that cannot be accessed or don't exist
        }
      }
    }
    log.debug("Built unique fields map with {} fields: {}", uniqueFields.size(), uniqueFields);
    return uniqueFields;
  }

  protected Map<String, Object> buildUniqueFieldsMap(T entity, Map<String, Object> additionalUniqueFields) {
    Map<String, Object> uniqueFields = buildUniqueFieldsMap(entity);
    if (additionalUniqueFields != null) {
      uniqueFields.putAll(additionalUniqueFields);
    }
    return uniqueFields;
  }

  protected Specification<T> buildUniqueFieldsSpec(Map<String, Object> uniqueFields) {
    log.debug("Building unique fields specification with fields: {}", uniqueFields);
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();
      uniqueFields.forEach((field, value) -> {
        Path<?> path = getPath(root, field);
        if (value != null) {
          predicates.add(cb.equal(path, value));
        } else {
          // For null values, use IS NULL predicate
          predicates.add(cb.isNull(path));
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

  public Optional<T> getByUniqueFields(Map<String, Object> uniqueFields) {
    if (uniqueFields == null || uniqueFields.isEmpty()) {
      return Optional.empty();
    }
    return getSpecificationExecutor().findOne(buildUniqueFieldsSpec(uniqueFields));
  }

  public boolean existsByUniqueFields(Map<String, Object> uniqueFields) {
    if (uniqueFields == null || uniqueFields.isEmpty()) {
      return false;
    }
    return getSpecificationExecutor().exists(buildUniqueFieldsSpec(uniqueFields));
  }

  public boolean existsByUniqueFieldsAndNotId(Map<String, Object> uniqueFields, K id) {
    if (uniqueFields == null || uniqueFields.isEmpty() || id == null) {
      return false;
    }
    Specification<T> spec = buildUniqueFieldsSpec(uniqueFields)
        .and((root, query, cb) -> cb.notEqual(root.get("id"), id));
    return getSpecificationExecutor().exists(spec);
  }

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

  /**
   * Hook for entity-specific validations before create.
   * Services may override and throw IllegalArgumentException or a custom
   * exception
   * to signal validation failures.
   */
  protected void validateForCreate(D dto, T entity) {
    // Default: no-op
  }

  /**
   * Hook for entity-specific validations before update (PUT).
   */
  protected void validateForUpdate(D dto, T entity) {
    // Default: no-op
  }

  /**
   * Hook for entity-specific validations before patch (PATCH).
   */
  protected void validateForPatch(D dto, T entity) {
    // Default: no-op
  }

  // ============================================================================
  // Uniqueness Checking Helper Methods
  // ============================================================================

  protected boolean isDuplicate(@NotNull T entity, @NotNull UniquenessStrategy strategy) {
    Objects.requireNonNull(entity, "Entity cannot be null");
    Objects.requireNonNull(strategy, "Uniqueness strategy cannot be null");

    Map<String, Object> uniqueFields = buildUniqueFieldsMap(entity);
    if (uniqueFields.isEmpty()) {
      log.debug("No unique fields found for duplicate checking");
      return false;
    }

    log.debug("Using duplicate checking with strategy {}", strategy);
    return switch (strategy) {
      case ALL -> existsByUniqueFields(uniqueFields);
      case ANY -> uniqueFields.entrySet().stream()
          .filter(entry -> entry.getValue() != null)
          .anyMatch(entry -> existsByUniqueFields(Map.of(entry.getKey(), entry.getValue())));
    };
  }

  protected boolean isDuplicate(@NotNull K id, @NotNull T entity, @NotNull UniquenessStrategy strategy) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(entity, "Entity cannot be null");
    Objects.requireNonNull(strategy, "Uniqueness strategy cannot be null");

    Map<String, Object> uniqueFields = buildUniqueFieldsMap(entity);
    if (uniqueFields.isEmpty()) {
      log.debug("No unique fields found for duplicate checking");
      return false;
    }

    log.debug("Using duplicate checking for ID {} with strategy {}", id, strategy);
    return switch (strategy) {
      case ALL -> existsByUniqueFieldsAndNotId(uniqueFields, id);
      case ANY -> uniqueFields.entrySet().stream()
          .filter(entry -> entry.getValue() != null)
          .anyMatch(entry -> existsByUniqueFieldsAndNotId(Map.of(entry.getKey(), entry.getValue()), id));
    };
  }

  protected abstract boolean isDuplicate(@NotNull T entity);

  protected abstract boolean isDuplicate(@NotNull K id, @NotNull T entity);

}
