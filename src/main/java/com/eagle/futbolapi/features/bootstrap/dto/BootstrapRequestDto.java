package com.eagle.futbolapi.features.bootstrap.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.JsonNode;

/**
 * Data Transfer Object for bootstrap requests.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BootstrapRequestDto {

  /**
   * The entity type to load (e.g., "country", "person", "player").
   * If not provided, will attempt to load all supported entities.
   */
  private String entityType;

  /**
   * The data to load. Can be a single object or array of objects.
   */
  private JsonNode data;

  /**
   * Path to the data file (relative to the data directory).
   * If provided, data will be loaded from this file instead of the request body.
   */
  private String dataFilePath;

  /**
   * Whether to clear existing data before loading.
   */
  @Builder.Default
  private Boolean clearExisting = false;
}
