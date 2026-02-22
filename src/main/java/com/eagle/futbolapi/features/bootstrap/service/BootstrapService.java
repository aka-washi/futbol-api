package com.eagle.futbolapi.features.bootstrap.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.bootstrap.dto.BootstrapRequestDto;
import com.eagle.futbolapi.features.bootstrap.dto.BootstrapResponseDto;
import com.eagle.futbolapi.features.country.dto.CountryDto;
import com.eagle.futbolapi.features.country.service.CountryService;
import com.eagle.futbolapi.features.organization.dto.OrganizationDto;
import com.eagle.futbolapi.features.organization.service.OrganizationService;
import com.eagle.futbolapi.features.person.dto.PersonDto;
import com.eagle.futbolapi.features.person.service.PersonService;
import com.eagle.futbolapi.features.player.dto.PlayerDto;
import com.eagle.futbolapi.features.player.service.PlayerService;
import com.eagle.futbolapi.features.pointsystem.dto.PointSystemDto;
import com.eagle.futbolapi.features.pointsystem.service.PointSystemService;
import com.eagle.futbolapi.features.season.dto.SeasonDto;
import com.eagle.futbolapi.features.season.service.SeasonService;
import com.eagle.futbolapi.features.staff.dto.StaffDto;
import com.eagle.futbolapi.features.staff.service.StaffService;
import com.eagle.futbolapi.features.team.dto.TeamDto;
import com.eagle.futbolapi.features.team.service.TeamService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

/**
 * Service for bootstrapping data into the application.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BootstrapService {

  private static final String DEFAULT_DATA_DIR = "input";

  private final ObjectMapper objectMapper;
  private final CountryService countryService;
  private final OrganizationService organizationService;
  private final PointSystemService pointSystemService;
  private final SeasonService seasonService;
  private final PersonService personService;
  private final PlayerService playerService;
  private final StaffService staffService;
  private final TeamService teamService;

  /**
   * Load data based on the bootstrap request.
   */
  @Transactional
  public BootstrapResponseDto loadData(BootstrapRequestDto request) {
    log.info("Starting bootstrap data load for entity type: {}", request.getEntityType());

    try {
      JsonNode data = getDataToLoad(request);

      if (data == null || data.isNull()) {
        return BootstrapResponseDto.builder()
            .entityType(request.getEntityType())
            .message("No data provided")
            .build();
      }

      return processData(request.getEntityType(), data);

    } catch (Exception e) {
      log.error("Error during bootstrap data load", e);
      return BootstrapResponseDto.builder()
          .entityType(request.getEntityType())
          .failureCount(1)
          .errors(List.of("Bootstrap failed: " + e.getMessage()))
          .message("Bootstrap failed")
          .build();
    }
  }

  /**
   * Get the data to load from either the request body or a file.
   */
  private JsonNode getDataToLoad(BootstrapRequestDto request) throws IOException {
    if (request.getData() != null && !request.getData().isNull()) {
      return request.getData();
    }

    if (request.getDataFilePath() != null && !request.getDataFilePath().trim().isEmpty()) {
      return loadDataFromFile(request.getDataFilePath());
    }

    // Try to load from default file
    if (request.getEntityType() != null) {
      String defaultFile = request.getEntityType() + ".json";
      return loadDataFromFile(defaultFile);
    }

    return null;
  }

  /**
   * Load data from a file in the data directory.
   */
  private JsonNode loadDataFromFile(String fileName) throws IOException {
    Path filePath = Paths.get(DEFAULT_DATA_DIR, fileName);
    File file = filePath.toFile();

    if (!file.exists()) {
      log.warn("Data file not found: {}", filePath);
      return null;
    }

    log.info("Loading data from file: {}", filePath);
    String content = Files.readString(filePath);
    return objectMapper.readTree(content);
  }

  /**
   * Process and load the data based on entity type.
   */
  private BootstrapResponseDto processData(String entityType, JsonNode data) {
    BootstrapResponseDto response = BootstrapResponseDto.builder()
        .entityType(entityType)
        .build();

    if (entityType == null || entityType.trim().isEmpty()) {
      response.addError("Entity type is required");
      response.incrementFailure();
      response.setMessage("Entity type is required");
      return response;
    }

    // Convert to array if single object
    JsonNode dataArray = data.isArray() ? data : objectMapper.createArrayNode().add(data);

    switch (entityType.toLowerCase()) {
      case "country":
        return loadEntities(dataArray, CountryDto.class, countryService::create, "Country");
      case "organization":
        return loadEntities(dataArray, OrganizationDto.class, organizationService::create, "Organization");
      case "pointsystem":
      case "point-system":
        return loadEntities(dataArray, PointSystemDto.class, pointSystemService::create, "PointSystem");
      case "season":
        return loadEntities(dataArray, SeasonDto.class, seasonService::create, "Season");
      case "person":
        return loadEntities(dataArray, PersonDto.class, personService::create, "Person");
      case "player":
        return loadEntities(dataArray, PlayerDto.class, playerService::create, "Player");
      case "staff":
        return loadEntities(dataArray, StaffDto.class, staffService::create, "Staff");
      case "team":
        return loadEntities(dataArray, TeamDto.class, teamService::create, "Team");
      default:
        response.addError("Unsupported entity type: " + entityType);
        response.incrementFailure();
        response.setMessage("Unsupported entity type");
        return response;
    }
  }

  /**
   * Load entities using the provided creation function.
   */
  private <D> BootstrapResponseDto loadEntities(JsonNode dataArray, Class<D> dtoClass,
      java.util.function.Function<D, ?> createFunction, String entityName) {
    BootstrapResponseDto response = BootstrapResponseDto.builder()
        .entityType(entityName)
        .build();

    for (JsonNode node : dataArray) {
      try {
        D dto = objectMapper.treeToValue(node, dtoClass);
        createFunction.apply(dto);
        response.incrementSuccess();
        log.debug("Successfully loaded {} entity", entityName);
      } catch (Exception e) {
        response.incrementFailure();
        String errorMsg = String.format("Failed to load %s: %s", entityName, e.getMessage());
        response.addError(errorMsg);
        log.error(errorMsg, e);
      }
    }

    String message = String.format("Loaded %d %s entities successfully, %d failed",
        response.getSuccessCount(), entityName, response.getFailureCount());
    response.setMessage(message);

    log.info(message);
    return response;
  }
}
