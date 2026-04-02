package com.eagle.futbolapi.features.bootstrap.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.bootstrap.dto.BootstrapRequestDto;
import com.eagle.futbolapi.features.bootstrap.dto.BootstrapResponseDto;
import com.eagle.futbolapi.features.bootstrap.service.BootstrapService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for bootstrap operations.
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/bootstrap")
@RequiredArgsConstructor
public class BootstrapController {

  private final BootstrapService bootstrapService;

  /**
   * Load data for bootstrapping the application.
   *
   * Example request to load from request body:
   * {
   *   "entityType": "country",
   *   "data": [
   *     {
   *       "name": "Mexico",
   *       "code": "MX",
   *       "isoCode": "MEX",
   *       "displayName": "Mexico"
   *     }
   *   ]
   * }
   *
   * Example request to load from file:
   * {
   *   "entityType": "country",
   *   "dataFilePath": "countries.json"
   * }
   *
   * @param request the bootstrap request
   * @return the bootstrap response with success/failure counts
   */
  @PostMapping("/load")
  public ResponseEntity<Map<String, Object>> loadData(@RequestBody BootstrapRequestDto request) {
    log.info("Bootstrap load request received for entity type: {}", request.getEntityType());

    try {
      BootstrapResponseDto response = bootstrapService.loadData(request);

      Map<String, Object> responseMap = new HashMap<>();
      responseMap.put("status", response.getFailureCount() > 0 ? "PARTIAL_SUCCESS" : "SUCCESS");
      responseMap.put("entityType", response.getEntityType());
      responseMap.put("successCount", response.getSuccessCount());
      responseMap.put("failureCount", response.getFailureCount());
      responseMap.put("message", response.getMessage());

      if (!response.getErrors().isEmpty()) {
        responseMap.put("errors", response.getErrors());
      }

      HttpStatus status = response.getFailureCount() > 0 && response.getSuccessCount() == 0
          ? HttpStatus.BAD_REQUEST
          : HttpStatus.OK;

      return ResponseEntity.status(status).body(responseMap);

    } catch (Exception e) {
      log.error("Error processing bootstrap request", e);

      Map<String, Object> errorResponse = new HashMap<>();
      errorResponse.put("status", "ERROR");
      errorResponse.put("message", "Bootstrap failed: " + e.getMessage());
      errorResponse.put("entityType", request.getEntityType());

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
  }

  /**
   * Health check endpoint for the bootstrap service.
   *
   * @return health status
   */
  @PostMapping("/health")
  public ResponseEntity<Map<String, String>> health() {
    Map<String, String> response = new HashMap<>();
    response.put("status", "UP");
    response.put("service", "Bootstrap Service");
    return ResponseEntity.ok(response);
  }

  /**
   * Load all entities in the proper dependency order.
   * This endpoint loads data from the default input folder for all entity types.
   *
   * Loading order:
   * 1. country
   * 2. organization
   * 3. pointsystem
   * 4. season
   * 5. person
   * 6. team
   * 7. tournament
   * 8. player
   * 9. staff
   * 10. stageFormat
   * 11. tournamentSeason
   * 12. competition
   * 13. registration
   * 15. stage
   *
   * @return summary of all load operations
   */
  @PostMapping("/load-all")
  public ResponseEntity<Map<String, Object>> loadAll() {
    log.info("Bootstrap load-all request received");

    try {
      Map<String, BootstrapResponseDto> results = bootstrapService.loadAll();

      int totalSuccess = results.values().stream().mapToInt(BootstrapResponseDto::getSuccessCount).sum();
      int totalFailure = results.values().stream().mapToInt(BootstrapResponseDto::getFailureCount).sum();

      Map<String, Object> responseMap = new HashMap<>();
      responseMap.put("status", totalFailure > 0 ? "PARTIAL_SUCCESS" : "SUCCESS");
      responseMap.put("totalSuccessCount", totalSuccess);
      responseMap.put("totalFailureCount", totalFailure);
      responseMap.put("results", results);
      responseMap.put("message", String.format("Loaded %d entities successfully, %d failed across all types",
          totalSuccess, totalFailure));

      HttpStatus status = totalFailure > 0 && totalSuccess == 0
          ? HttpStatus.BAD_REQUEST
          : HttpStatus.OK;

      return ResponseEntity.status(status).body(responseMap);

    } catch (Exception e) {
      log.error("Error processing bootstrap load-all request", e);

      Map<String, Object> errorResponse = new HashMap<>();
      errorResponse.put("status", "ERROR");
      errorResponse.put("message", "Bootstrap load-all failed: " + e.getMessage());

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
  }
}
