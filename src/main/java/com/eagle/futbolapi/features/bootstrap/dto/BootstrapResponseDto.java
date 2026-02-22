package com.eagle.futbolapi.features.bootstrap.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for bootstrap responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BootstrapResponseDto {

  private String entityType;

  @Builder.Default
  private Integer successCount = 0;

  @Builder.Default
  private Integer failureCount = 0;

  @Builder.Default
  private List<String> errors = new ArrayList<>();

  private String message;

  public void incrementSuccess() {
    this.successCount++;
  }

  public void incrementFailure() {
    this.failureCount++;
  }

  public void addError(String error) {
    this.errors.add(error);
  }
}
