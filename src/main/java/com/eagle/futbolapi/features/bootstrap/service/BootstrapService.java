package com.eagle.futbolapi.features.bootstrap.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.eagle.futbolapi.features.bootstrap.dto.BootstrapRequestDto;
import com.eagle.futbolapi.features.bootstrap.dto.BootstrapResponseDto;
import com.eagle.futbolapi.features.competition.dto.CompetitionDto;
import com.eagle.futbolapi.features.competition.service.CompetitionService;
import com.eagle.futbolapi.features.country.dto.CountryDto;
import com.eagle.futbolapi.features.country.service.CountryService;
import com.eagle.futbolapi.features.group.dto.GroupDto;
import com.eagle.futbolapi.features.group.service.GroupService;
import com.eagle.futbolapi.features.lineup.dto.LineupDto;
import com.eagle.futbolapi.features.lineup.service.LineupService;
import com.eagle.futbolapi.features.lineupMember.dto.LineupMemberDto;
import com.eagle.futbolapi.features.lineupMember.service.LineupMemberService;
import com.eagle.futbolapi.features.match.dto.MatchDto;
import com.eagle.futbolapi.features.match.service.MatchService;
import com.eagle.futbolapi.features.matchday.dto.MatchdayDto;
import com.eagle.futbolapi.features.matchday.service.MatchdayService;
import com.eagle.futbolapi.features.matchevent.dto.MatchEventDto;
import com.eagle.futbolapi.features.matchevent.service.MatchEventService;
import com.eagle.futbolapi.features.organization.dto.OrganizationDto;
import com.eagle.futbolapi.features.organization.service.OrganizationService;
import com.eagle.futbolapi.features.person.dto.PersonDto;
import com.eagle.futbolapi.features.person.service.PersonService;
import com.eagle.futbolapi.features.player.dto.PlayerDto;
import com.eagle.futbolapi.features.player.service.PlayerService;
import com.eagle.futbolapi.features.pointsystem.dto.PointSystemDto;
import com.eagle.futbolapi.features.pointsystem.service.PointSystemService;
import com.eagle.futbolapi.features.registration.dto.RegistrationDto;
import com.eagle.futbolapi.features.registration.service.RegistrationService;
import com.eagle.futbolapi.features.season.dto.SeasonDto;
import com.eagle.futbolapi.features.season.service.SeasonService;
import com.eagle.futbolapi.features.staff.dto.StaffDto;
import com.eagle.futbolapi.features.staff.service.StaffService;
import com.eagle.futbolapi.features.stage.dto.StageDto;
import com.eagle.futbolapi.features.stage.service.StageService;
import com.eagle.futbolapi.features.stageFormat.dto.StageFormatDto;
import com.eagle.futbolapi.features.stageFormat.service.StageFormatService;
import com.eagle.futbolapi.features.standing.dto.StandingDto;
import com.eagle.futbolapi.features.standing.service.StandingService;
import com.eagle.futbolapi.features.team.dto.TeamDto;
import com.eagle.futbolapi.features.team.service.TeamService;
import com.eagle.futbolapi.features.tournament.dto.TournamentDto;
import com.eagle.futbolapi.features.tournament.service.TournamentService;
import com.eagle.futbolapi.features.tournamentSeason.dto.TournamentSeasonDto;
import com.eagle.futbolapi.features.tournamentSeason.service.TournamentSeasonService;
import com.eagle.futbolapi.features.venue.dto.VenueDto;
import com.eagle.futbolapi.features.venue.service.VenueService;

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
  private final TournamentService tournamentService;
  private final RegistrationService registrationService;
  private final StageFormatService stageFormatService;
  private final TournamentSeasonService tournamentSeasonService;
  private final CompetitionService competitionService;
  private final StageService stageService;
  private final VenueService venueService;
  private final GroupService groupService;
  private final MatchdayService matchdayService;
  private final StandingService standingService;
  private final MatchService matchService;
  private final LineupService lineupService;
  private final LineupMemberService lineupMemberService;
  private final MatchEventService matchEventService;
  private final BootstrapTransactionHelper transactionHelper;

  /**
   * Load data based on the bootstrap request.
   * Each entity is saved in its own transaction to allow partial success.
   */
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
   * Load all entities in the proper dependency order.
   * This method attempts to load data for all entity types from the default input folder.
   *
   * @return map of entity type to bootstrap response
   */
  public Map<String, BootstrapResponseDto> loadAll() {
    log.info("Starting bootstrap load-all operation");

    Map<String, BootstrapResponseDto> results = new LinkedHashMap<>();

    // Define loading order based on dependencies
    String[] entityTypes = {
        "country",           // No dependencies
        "organization",      // Depends on country
        "pointsystem",       // No dependencies
        "season",            // No dependencies
        "person",            // No dependencies
        "venue",             // Depends on country
        "team",              // Depends on organization, country, venue
        "tournament",        // Depends on organization
        "player",            // Depends on person, team
        "staff",             // Depends on person, team
        "stageformat",       // Depends on pointsystem (optional)
        "tournamentseason",  // Depends on tournament, season
        "competition",       // Depends on tournamentSeason
        "registration",      // Depends on person, team, tournament, competition
        "stage",             // Depends on competition, stageFormat (optional)
        "group",             // Depends on stage
        "matchday",          // Depends on stage
        "standing",          // Depends on stage, team
        "match",             // Depends on matchday, venue, team
        "lineup",            // Depends on match, team
        "lineupmember",      // Depends on lineup, player, staff
        "matchevent"         // Depends on match, player
    };

    for (String entityType : entityTypes) {
      try {
        log.info("Loading entity type: {}", entityType);
        BootstrapRequestDto request = new BootstrapRequestDto();
        request.setEntityType(entityType);

        BootstrapResponseDto response = loadData(request);
        results.put(entityType, response);

        log.info("Completed loading {}: {} success, {} failures",
            entityType, response.getSuccessCount(), response.getFailureCount());

      } catch (Exception e) {
        log.error("Error loading entity type: {}", entityType, e);
        BootstrapResponseDto errorResponse = BootstrapResponseDto.builder()
            .entityType(entityType)
            .failureCount(1)
            .errors(List.of("Failed to load: " + e.getMessage()))
            .message("Load failed")
            .build();
        results.put(entityType, errorResponse);
      }
    }

    log.info("Bootstrap load-all operation completed");
    return results;
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
   * Tries multiple naming conventions: exact match, lowercase, and camelCase variants.
   */
  private JsonNode loadDataFromFile(String fileName) throws IOException {
    // Try exact file name first
    Path filePath = Paths.get(DEFAULT_DATA_DIR, fileName);
    File file = filePath.toFile();

    if (file.exists()) {
      log.info("Loading data from file: {}", filePath);
      String content = Files.readString(filePath);
      return objectMapper.readTree(content);
    }

    // Try camelCase variants for compound names
    String camelCaseFileName = toCamelCaseFileName(fileName);
    if (!camelCaseFileName.equals(fileName)) {
      filePath = Paths.get(DEFAULT_DATA_DIR, camelCaseFileName);
      file = filePath.toFile();

      if (file.exists()) {
        log.info("Loading data from file: {}", filePath);
        String content = Files.readString(filePath);
        return objectMapper.readTree(content);
      }
    }

    log.warn("Data file not found: {} (also tried: {})",
        Paths.get(DEFAULT_DATA_DIR, fileName), camelCaseFileName);
    return null;
  }

  /**
   * Convert a filename to camelCase variant.
   * Examples: "stageformat.json" -> "stageFormat.json"
   *           "tournamentseason.json" -> "tournamentSeason.json"
   */
  private String toCamelCaseFileName(String fileName) {
    if (!fileName.contains(".")) {
      return fileName;
    }

    String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
    String extension = fileName.substring(fileName.lastIndexOf('.'));

    // Common compound entity names
    String[][] replacements = {
        {"stageformat", "stageFormat"},
        {"tournamentseason", "tournamentSeason"},
        {"pointsystem", "pointSystem"},
        {"matchday", "matchDay"},
        {"lineupmember", "lineupMember"},
        {"matchevent", "matchEvent"}
    };

    for (String[] replacement : replacements) {
      if (baseName.equalsIgnoreCase(replacement[0])) {
        return replacement[1] + extension;
      }
    }

    return fileName;
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
      case "venue":
        return loadEntities(dataArray, VenueDto.class, venueService::create, "Venue");
      case "player":
        return loadEntities(dataArray, PlayerDto.class, playerService::create, "Player");
      case "staff":
        return loadEntities(dataArray, StaffDto.class, staffService::create, "Staff");
      case "team":
        return loadEntities(dataArray, TeamDto.class, teamService::create, "Team");
      case "tournament":
        return loadEntities(dataArray, TournamentDto.class, tournamentService::create, "Tournament");
      case "registration":
        return loadEntities(dataArray, RegistrationDto.class, registrationService::create, "Registration");
      case "stageformat":
      case "stage-format":
        return loadEntities(dataArray, StageFormatDto.class, stageFormatService::create, "StageFormat");
      case "tournamentseason":
      case "tournament-season":
        return loadEntities(dataArray, TournamentSeasonDto.class, tournamentSeasonService::create, "TournamentSeason");
      case "competition":
        return loadEntities(dataArray, CompetitionDto.class, competitionService::create, "Competition");
      case "stage":
        return loadEntities(dataArray, StageDto.class, stageService::create, "Stage");
      case "group":
        return loadEntities(dataArray, GroupDto.class, groupService::create, "Group");
      case "matchday":
        return loadEntities(dataArray, MatchdayDto.class, matchdayService::create, "Matchday");
      case "standing":
        return loadEntities(dataArray, StandingDto.class, standingService::create, "Standing");
      case "match":
        return loadEntities(dataArray, MatchDto.class, matchService::create, "Match");
      case "lineup":
        return loadEntities(dataArray, LineupDto.class, lineupService::create, "Lineup");
      case "lineupmember":
      case "lineup-member":
        return loadEntities(dataArray, LineupMemberDto.class, lineupMemberService::create, "LineupMember");
      case "matchevent":
      case "match-event":
        return loadEntities(dataArray, MatchEventDto.class, matchEventService::create, "MatchEvent");
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
        transactionHelper.createEntityInNewTransaction(dto, createFunction);
        response.incrementSuccess();
        log.debug("Successfully loaded {} entity", entityName);
      } catch (Exception e) {
        response.incrementFailure();
        String errorMsg = buildErrorMessage(entityName, e);
        response.addError(errorMsg);
        log.warn(errorMsg);
      }
    }

    String message = String.format("Loaded %d %s entities successfully, %d failed",
        response.getSuccessCount(), entityName, response.getFailureCount());
    response.setMessage(message);

    log.info(message);
    return response;
  }

  /**
   * Build a user-friendly error message from an exception.
   * Detects duplicate errors and provides a friendlier message.
   */
  private String buildErrorMessage(String entityName, Exception e) {
    // Check if it's a duplicate error
    if (isDuplicateError(e)) {
      return String.format("Failed to load %s: Duplicate entity", entityName);
    }

    // For other errors, return the exception message
    return String.format("Failed to load %s: %s", entityName, e.getMessage());
  }

  /**
   * Check if an exception indicates a duplicate/unique constraint violation.
   */
  private boolean isDuplicateError(Exception e) {
    // Check if the exception is or contains a DataIntegrityViolationException
    Throwable cause = e;
    while (cause != null) {
      if (cause instanceof DataIntegrityViolationException) {
        String message = cause.getMessage();
        if (message != null) {
          // Check for common unique constraint violation patterns
          return message.contains("Unique index or primary key violation") ||
                 message.contains("unique constraint") ||
                 message.contains("duplicate key") ||
                 message.contains("UNIQUE KEY") ||
                 message.contains("23505") ||  // PostgreSQL unique violation error code
                 message.contains("ORA-00001"); // Oracle unique constraint error code
        }
        return true;
      }
      cause = cause.getCause();
    }
    return false;
  }
}
