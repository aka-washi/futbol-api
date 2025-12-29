package com.eagle.futbolapi.features.shared.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.eagle.futbolapi.features.shared.ApiResponse;
import com.eagle.futbolapi.features.shared.ResponseUtil;
import com.eagle.futbolapi.features.shared.exception.DuplicateResourceException;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;

/**
 * Generic base controller for CRUD operations.
 * @param <E> Entity type
 * @param <D> DTO type
 * @param <S> Service type
 * @param <M> Mapper type
 */
@Validated
public abstract class BaseCrudController<E, D, S, M> {

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

    protected BaseCrudController(S service, M mapper, String resourceName, String successMessage, String duplicateMessage, String serverError) {
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
        Page<E> entityPage = getAllEntities(pageable);
        Page<D> dtoPage = entityPage.map(this::toDTO);
        return ResponseUtil.successWithPagination(dtoPage, successMessage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<D>> getById(@PathVariable Long id) {
        E entity = getEntityById(id);
        D dto = toDTO(entity);
        return ResponseUtil.success(dto, successMessage);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<D>> create(@Valid @RequestBody D dto) {
        try {
            E entity = toEntity(dto);
            E savedEntity = createEntity(entity);
            D savedDto = toDTO(savedEntity);
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
            E entity = toEntity(dto);
            E updatedEntity = updateEntity(id, entity);
            D updatedDto = toDTO(updatedEntity);
            return ResponseUtil.success(updatedDto, resourceName + " updated successfully");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("NOT_FOUND", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(serverError, "Error updating " + resourceName.toLowerCase() + ": " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        try {
            deleteEntity(id);
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
        boolean exists = existsById(id);
        return ResponseUtil.success(exists, resourceName + " existence check completed");
    }

    // Abstract methods to be implemented by subclasses
    protected abstract Page<E> getAllEntities(Pageable pageable);
    protected abstract E getEntityById(Long id);
    protected abstract E createEntity(E entity);
    protected abstract E updateEntity(Long id, E entity);
    protected abstract void deleteEntity(Long id);
    protected abstract boolean existsById(Long id);
    protected abstract D toDTO(E entity);
    protected abstract E toEntity(D dto);
}
