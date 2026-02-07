package com.eagle.futbolapi.features.base.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.eagle.futbolapi.features.base.dto.ApiResponse;
import com.eagle.futbolapi.features.base.entity.BaseEntity;
import com.eagle.futbolapi.features.base.exception.DuplicateResourceException;
import com.eagle.futbolapi.features.base.exception.NoChangesDetectedException;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.base.util.ResponseUtil;

/**
 * Generic base controller for CRUD operations.
 *
 * @param <E> Entity type (must extend BaseEntity)
 * @param <D> DTO type
 * @param <S> Service type (must extend BaseCrudService)
 * @param <M> Mapper type (must extend BaseMapper)
 */
@Validated
public abstract class BaseCrudController<E extends BaseEntity, D, S extends BaseCrudService<E, Long, D>, M extends BaseMapper<E, D>> {

  private static final String DEFAULT_PAGE = "0";
  private static final int MIN_DEFAULT_PAGE = 0;
  private static final String DEFAULT_PAGE_SIZE = "20";
  private static final int MIN_PAGE_SIZE = 1;
  private static final String DEFAULT_SORT_FIELD = "name";
  private static final String DEFAULT_SORT_DIRECTION = "asc";

  protected final S service;
  protected final M mapper;
  protected final String resourceName;
  protected final String successMessage;
  protected final String duplicateMessage;
  protected final String serverError;

  protected BaseCrudController(S service, M mapper, String resourceName, String successMessage, String duplicateMessage,
      String serverError) {
    this.service = service;
    this.mapper = mapper;
    this.resourceName = resourceName;
    this.successMessage = successMessage;
    this.duplicateMessage = duplicateMessage;
    this.serverError = serverError;
  }

  @GetMapping
  public ResponseEntity<ApiResponse<Page<D>>> getAll(
      @RequestParam(defaultValue = DEFAULT_PAGE) @Min(MIN_DEFAULT_PAGE) int page,
      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) @Min(MIN_PAGE_SIZE) int size,
      @RequestParam(defaultValue = DEFAULT_SORT_FIELD) String sortBy,
      @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION) String sortDir) {
    Pageable pageable = ResponseUtil.buildPageable(page, size, sortBy, sortDir);
    Page<E> entityPage = service.getAll(pageable);
    Page<D> dtoPage = entityPage.map(mapper::toDTO);
    return ResponseUtil.successWithPagination(dtoPage, successMessage);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<D>> getById(@PathVariable Long id) {
    E entity = service.getById(id)
        .orElseThrow(() -> new ResourceNotFoundException(resourceName, "id", id));
    D dto = mapper.toDTO(entity);
    return ResponseUtil.success(dto, successMessage);
  }

  @PostMapping
  public ResponseEntity<ApiResponse<D>> create(@Valid @RequestBody D dto) {
    try {
      E savedEntity = service.create(dto);
      D savedDto = mapper.toDTO(savedEntity);
      return ResponseUtil.created(savedDto, resourceName + " created successfully");
    } catch (DuplicateResourceException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(ApiResponse.error("DUPLICATE_ERROR", duplicateMessage));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ApiResponse.error(serverError, "Error creating " + resourceName.toLowerCase() + ": " + e.getMessage()));
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<D>> update(@PathVariable Long id, @Valid @RequestBody D dto) {
    try {
      E updatedEntity = service.update(id, dto);
      D updatedDto = mapper.toDTO(updatedEntity);
      return ResponseUtil.success(updatedDto, resourceName + " updated successfully");
    } catch (NoChangesDetectedException e) {
      return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
          .body(ApiResponse.error("NO_CHANGES", e.getMessage()));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(ApiResponse.error("NOT_FOUND", e.getMessage()));
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ApiResponse.error(serverError, "Error updating " + resourceName.toLowerCase() + ": " + e.getMessage()));
    }
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ApiResponse<D>> patch(@PathVariable Long id, @RequestBody D dto) {
    try {
      E patchedEntity = service.patch(id, dto);
      D patchedDto = mapper.toDTO(patchedEntity);
      return ResponseUtil.success(patchedDto, resourceName + " updated successfully");
    } catch (NoChangesDetectedException e) {
      return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
          .body(ApiResponse.error("NO_CHANGES", e.getMessage()));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(ApiResponse.error("NOT_FOUND", e.getMessage()));
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ApiResponse.error(serverError, "Error patching " + resourceName.toLowerCase() + ": " + e.getMessage()));
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    try {
      service.delete(id);
      return ResponseUtil.success(null, resourceName + " deleted successfully");
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(ApiResponse.error("NOT_FOUND", e.getMessage()));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ApiResponse.error(serverError, "Error deleting " + resourceName.toLowerCase() + ": " + e.getMessage()));
    }
  }

  @GetMapping("/{id}/exists")
  public ResponseEntity<ApiResponse<Boolean>> exists(@PathVariable Long id) {
    boolean exists = service.existsById(id);
    return ResponseUtil.success(exists, resourceName + " existence check completed");
  }
}
