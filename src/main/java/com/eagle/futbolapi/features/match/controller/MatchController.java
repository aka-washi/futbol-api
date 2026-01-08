package com.eagle.futbolapi.features.match.controller;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.match.dto.MatchDTO;
import com.eagle.futbolapi.features.match.entity.Match;
import com.eagle.futbolapi.features.match.entity.MatchStatus;
import com.eagle.futbolapi.features.match.service.MatchService;
import com.eagle.futbolapi.features.shared.ApiResponse;
import com.eagle.futbolapi.features.shared.ResponseUtil;
import com.eagle.futbolapi.features.shared.controller.BaseCrudController;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/matches")
@Validated
public class MatchController extends BaseCrudController<Match, MatchDTO, MatchService, Object> {

    public MatchController(MatchService matchService) {
        super(
                matchService,
                null, // Mapper not available yet
                "Match",
                "Match retrieved successfully",
                "Match already exists",
                "SERVER_ERROR");
    }

    // Match-specific endpoints
    @GetMapping("/matchday/{matchdayId}")
    public ResponseEntity<ApiResponse<Page<MatchDTO>>> getMatchesByMatchdayId(
            @PathVariable Long matchdayId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "scheduledDate") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Pageable pageable = ResponseUtil.buildPageable(page, size, sortBy, sortDir);
        Page<Match> matches = service.getMatchesByMatchdayId(matchdayId, pageable);
        Page<MatchDTO> matchDTOs = matches.map(this::toDTO);
        return ResponseUtil.successWithPagination(matchDTOs, successMessage);
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<ApiResponse<Page<MatchDTO>>> getMatchesByTeamId(
            @PathVariable Long teamId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "scheduledDate") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Pageable pageable = ResponseUtil.buildPageable(page, size, sortBy, sortDir);
        Page<Match> matches = service.getMatchesByTeamId(teamId, pageable);
        Page<MatchDTO> matchDTOs = matches.map(this::toDTO);
        return ResponseUtil.successWithPagination(matchDTOs, successMessage);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<Page<MatchDTO>>> getMatchesByStatus(
            @PathVariable MatchStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "scheduledDate") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Pageable pageable = ResponseUtil.buildPageable(page, size, sortBy, sortDir);
        Page<Match> matches = service.getMatchesByStatus(status, pageable);
        Page<MatchDTO> matchDTOs = matches.map(this::toDTO);
        return ResponseUtil.successWithPagination(matchDTOs, successMessage);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<ApiResponse<Page<MatchDTO>>> getMatchesByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "kickoffTime") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Pageable pageable = ResponseUtil.buildPageable(page, size, sortBy, sortDir);
        Page<Match> matches = service.getMatchesByDate(date, pageable);
        Page<MatchDTO> matchDTOs = matches.map(this::toDTO);
        return ResponseUtil.successWithPagination(matchDTOs, successMessage);
    }

    // Implement abstract methods from BaseCrudController
    @Override
    protected Page<Match> getAllEntities(Pageable pageable) {
        return service.getAll(pageable);
    }

    @Override
    protected Match getEntityById(Long id) {
        return service.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, "id", id));
    }

    @Override
    protected Match createEntity(Match entity) {
        return service.create(entity);
    }

    @Override
    protected Match updateEntity(Long id, Match entity) {
        return service.update(id, entity);
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
    protected MatchDTO toDTO(Match entity) {
        // Simple DTO conversion (would be better with mapper)
        return MatchDTO.builder()
                .id(entity.getId())
                .build();
    }

    @Override
    protected Match toEntity(MatchDTO dto) {
        // Simple entity conversion (would be better with mapper)
        return Match.builder()
                .id(dto.getId())
                .build();
    }
}
