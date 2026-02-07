
package com.eagle.futbolapi.features.base.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

  private boolean success;
  private String message;
  private T data;
  private ApiError error;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  @Builder.Default
  private LocalDateTime timestamp = LocalDateTime.now();

  private PaginationInfo pagination;

  public static <T> ApiResponse<T> success(T data) {
    return ApiResponse.<T>builder()
        .success(true)
        .message("Operation successful")
        .data(data)
        .build();
  }

  public static <T> ApiResponse<T> success(T data, String message) {
    return ApiResponse.<T>builder()
        .success(true)
        .message(message)
        .data(data)
        .build();
  }

  public static <T> ApiResponse<T> success(T data, PaginationInfo pagination) {
    return ApiResponse.<T>builder()
        .success(true)
        .message("Operation successful")
        .data(data)
        .pagination(pagination)
        .build();
  }

  public static <T> ApiResponse<T> error(String message) {
    return ApiResponse.<T>builder()
        .success(false)
        .message(message)
        .error(new ApiError("GENERAL_ERROR", message))
        .build();
  }

  public static <T> ApiResponse<T> error(String errorCode, String message) {
    return ApiResponse.<T>builder()
        .success(false)
        .message(message)
        .error(new ApiError(errorCode, message))
        .build();
  }
}
