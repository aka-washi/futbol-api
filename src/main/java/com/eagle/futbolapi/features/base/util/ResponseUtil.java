
package com.eagle.futbolapi.features.base.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.eagle.futbolapi.features.base.dto.ApiResponse;
import com.eagle.futbolapi.features.base.dto.PaginationInfo;

/**
 * Utility class for building standardized API responses.
 * Provides common patterns for success and error responses with pagination
 * support.
 */
public final class ResponseUtil {

  private static final String DESCENDING = "desc";

  private ResponseUtil() {
    // Utility class should not be instantiated
  }

  /**
   * Build a successful response with data
   */
  public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message) {
    return ResponseEntity.ok(ApiResponse.success(data, message));
  }

  /**
   * Build a successful response with paginated data
   */
  public static <T> ResponseEntity<ApiResponse<Page<T>>> successWithPagination(
      Page<T> page, String message) {
    PaginationInfo paginationInfo = PaginationInfo.builder()
        .page(page.getNumber())
        .size(page.getSize())
        .totalElements(page.getTotalElements())
        .totalPages(page.getTotalPages())
        .first(page.isFirst())
        .last(page.isLast())
        .hasNext(page.hasNext())
        .hasPrevious(page.hasPrevious())
        .build();

    ApiResponse<Page<T>> response = ApiResponse.<Page<T>>builder()
        .success(true)
        .message(message)
        .data(page)
        .pagination(paginationInfo)
        .build();

    return ResponseEntity.ok(response);
  }

  /**
   * Build a created response (201)
   */
  public static <T> ResponseEntity<ApiResponse<T>> created(T data, String message) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(data, message));
  }

  /**
   * Build an error response with custom status code
   */
  public static <T> ResponseEntity<ApiResponse<T>> error(
      HttpStatus status, String errorCode, String message) {
    return ResponseEntity.status(status)
        .body(ApiResponse.error(errorCode, message));
  }

  /**
   * Build a not found error response (404)
   */
  public static <T> ResponseEntity<ApiResponse<T>> notFound(String message) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ApiResponse.error("RESOURCE_NOT_FOUND", message));
  }

  /**
   * Build an internal server error response (500)
   */
  public static <T> ResponseEntity<ApiResponse<T>> serverError(String message) {
    return error(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER_ERROR", message);
  }

  public static Pageable buildPageable(int page, int size, String sortBy, String sortDir) {
    Sort sort = DESCENDING.equalsIgnoreCase(sortDir)
        ? Sort.by(sortBy).descending()
        : Sort.by(sortBy).ascending();
    return PageRequest.of(page, size, sort);
  }

}
