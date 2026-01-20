package com.eagle.futbolapi.features.season.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.base.dto.ApiResponse;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.util.ResponseUtil;
import com.eagle.futbolapi.features.season.dto.SeasonDTO;
import com.eagle.futbolapi.features.season.entity.Season;
import com.eagle.futbolapi.features.season.mapper.SeasonMapper;
import com.eagle.futbolapi.features.season.service.SeasonService;

@RestController
@RequestMapping("/seasons")
@Validated
public class SeasonController extends BaseCrudController<Season, SeasonDTO, SeasonService, SeasonMapper> {

    private static final String RESOURCE_NAME = "Season";
    private static final String SUCCESS_MESSAGE = "Season retrieved successfully";
    private static final String DUPLICATE_MESSAGE = "Season already exists";
    private static final String SERVER_ERROR = "SERVER_ERROR";

    public SeasonController(SeasonService seasonService, SeasonMapper seasonMapper) {
        super(
                seasonService,
                seasonMapper,
                RESOURCE_NAME,
                SUCCESS_MESSAGE,
                DUPLICATE_MESSAGE,
                SERVER_ERROR);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse<SeasonDTO>> getSeasonByName(@PathVariable String name) {
        Season season = service.getSeasonByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, "name", name));
        SeasonDTO seasonDTO = mapper.toSeasonDTO(season);
        return ResponseUtil.success(seasonDTO, successMessage);
    }

    @GetMapping("/displayName/{displayName}")
    public ResponseEntity<ApiResponse<SeasonDTO>> getSeasonByDisplayName(@PathVariable String displayName) {
        Season season = service.getSeasonByDisplayName(displayName)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, "displayName", displayName));
        SeasonDTO seasonDTO = mapper.toSeasonDTO(season);
        return ResponseUtil.success(seasonDTO, successMessage);
    }

    @GetMapping("/tournament/{tournamentId}/active/{active}")
    public ResponseEntity<ApiResponse<SeasonDTO>> getSeasonByTournamentAndActive(
            @PathVariable Long tournamentId,
            @PathVariable Boolean active) {
        Season season = service.getSeasonByTournamentAndActive(tournamentId, active)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, "tournamentId and active", tournamentId + ", " + active));
        SeasonDTO seasonDTO = mapper.toSeasonDTO(season);
        return ResponseUtil.success(seasonDTO, successMessage);
    }

    @GetMapping("/tournament/{tournamentId}/date/{date}")
    public ResponseEntity<ApiResponse<SeasonDTO>> getSeasonByTournamentAndDateRange(
            @PathVariable Long tournamentId,
            @PathVariable String date) {
        Season season = service.getSeasonByTournamentAndDateRange(tournamentId, java.time.LocalDate.parse(date))
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, "tournamentId and date", tournamentId + ", " + date));
        SeasonDTO seasonDTO = mapper.toSeasonDTO(season);
        return ResponseUtil.success(seasonDTO, successMessage);
    }

    @GetMapping("/uniqueValues")
    public ResponseEntity<ApiResponse<Optional<Season>>> getSeasonByUniqueValues(
            @PathVariable Long tournamentId,
            @PathVariable String name,
            @PathVariable String displayName,
            @PathVariable String startDate,
            @PathVariable String endDate,
            @PathVariable Boolean active) {
        Optional<Season> exists = service.getSeasonByUniqueValues(
                tournamentId,
                name,
                LocalDate.parse(startDate),
                LocalDate.parse(endDate),
                active);
        return ResponseUtil.success(exists, successMessage);
    }

    // Implement abstract methods from BaseCrudController
    @Override
    protected Page<Season> getAllEntities(Pageable pageable) {
        return service.getAll(pageable);
    }

    @Override
    protected Season getEntityById(Long id) {
        return service.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, "id", id));
    }

    @Override
    protected Season createEntity(SeasonDTO dto) {
        return service.create(dto);
    }

    @Override
    protected Season updateEntity(Long id, SeasonDTO dto) {
        return service.update(id, dto);
    }
    @Override
    protected void deleteEntity(Long id) {
        service.delete(id);
    }

    @Override
    protected boolean existsById(Long id) {
        return service.existsById(id);
    }

    @Override
    protected SeasonDTO toDTO(Season entity) {
        return mapper.toSeasonDTO(entity);
    }

    @Override
    protected Season toEntity(SeasonDTO dto) {
        return mapper.toSeason(dto);
    }

}
