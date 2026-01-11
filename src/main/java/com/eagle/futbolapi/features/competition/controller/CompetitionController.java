package com.eagle.futbolapi.features.competition.controller;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.competition.dto.CompetitionDTO;
import com.eagle.futbolapi.features.competition.entity.Competition;
import com.eagle.futbolapi.features.competition.entity.CompetitionType;
import com.eagle.futbolapi.features.competition.mapper.CompetitionMapper;
import com.eagle.futbolapi.features.competition.service.CompetitionService;
import com.eagle.futbolapi.features.shared.ApiResponse;
import com.eagle.futbolapi.features.shared.ResponseUtil;
import com.eagle.futbolapi.features.shared.controller.BaseCrudController;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/competitions")
@Validated
public class CompetitionController extends BaseCrudController<Competition, CompetitionDTO, CompetitionService, CompetitionMapper> {

    public CompetitionController(CompetitionService competitionService, CompetitionMapper competitionMapper) {
        super(
            competitionService,
            competitionMapper,
            "Competition",
            "Competition retrieved successfully",
            "Competition already exists",
            "SERVER_ERROR"
        );
    }

    // Competition-specific endpoints
    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse<CompetitionDTO>> getCompetitionByName(@PathVariable String name) {
        Competition competition = service.getCompetitionByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, "name", name));
        CompetitionDTO competitionDTO = mapper.toCompetitionDTO(competition);
        return ResponseUtil.success(competitionDTO, successMessage);
    }

    @GetMapping("/displayName/{displayName}")
    public ResponseEntity<ApiResponse<CompetitionDTO>> getCompetitionByDisplayName(@PathVariable String displayName) {
        Competition competition = service.getCompetitionByDisplayName(displayName)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, "displayName", displayName));
        CompetitionDTO competitionDTO = mapper.toCompetitionDTO(competition);
        return ResponseUtil.success(competitionDTO, successMessage);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<ApiResponse<List<CompetitionDTO>>> getCompetitionsByType(@PathVariable String type) {
        try {
            CompetitionType competitionType = CompetitionType.valueOf(type.toUpperCase());
            List<Competition> competitions = service.getCompetitionsByType(competitionType);
            List<CompetitionDTO> competitionDTOs = competitions.stream()
                    .map(mapper::toCompetitionDTO)
                    .toList();
            return ResponseUtil.success(competitionDTOs, "Competitions retrieved successfully");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid competition type: " + type);
        }
    }

    @GetMapping("/season/{seasonId}")
    public ResponseEntity<ApiResponse<List<CompetitionDTO>>> getCompetitionsBySeason(@PathVariable @NotNull Long seasonId) {
        List<Competition> competitions = service.getCompetitionsBySeason(seasonId);
        List<CompetitionDTO> competitionDTOs = competitions.stream()
                .map(mapper::toCompetitionDTO)
                .toList();
        return ResponseUtil.success(competitionDTOs, "Competitions retrieved successfully");
    }

    @GetMapping("/season/{seasonId}/type/{type}")
    public ResponseEntity<ApiResponse<List<CompetitionDTO>>> getCompetitionsBySeasonAndType(
            @PathVariable @NotNull Long seasonId,
            @PathVariable String type) {
        try {
            CompetitionType competitionType = CompetitionType.valueOf(type.toUpperCase());
            List<Competition> competitions = service.getCompetitionsBySeasonAndType(seasonId, competitionType);
            List<CompetitionDTO> competitionDTOs = competitions.stream()
                    .map(mapper::toCompetitionDTO)
                    .toList();
            return ResponseUtil.success(competitionDTOs, "Competitions retrieved successfully");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid competition type: " + type);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<CompetitionDTO>>> getActiveCompetitions() {
        List<Competition> competitions = service.getActiveCompetitions();
        List<CompetitionDTO> competitionDTOs = competitions.stream()
                .map(mapper::toCompetitionDTO)
                .toList();
        return ResponseUtil.success(competitionDTOs, "Active competitions retrieved successfully");
    }

    @GetMapping("/inactive")
    public ResponseEntity<ApiResponse<List<CompetitionDTO>>> getInactiveCompetitions() {
        List<Competition> competitions = service.getInactiveCompetitions();
        List<CompetitionDTO> competitionDTOs = competitions.stream()
                .map(mapper::toCompetitionDTO)
                .toList();
        return ResponseUtil.success(competitionDTOs, "Inactive competitions retrieved successfully");
    }

    @GetMapping("/season/{seasonId}/active")
    public ResponseEntity<ApiResponse<List<CompetitionDTO>>> getActiveCompetitionsBySeason(@PathVariable @NotNull Long seasonId) {
        List<Competition> competitions = service.getActiveCompetitionsBySeason(seasonId);
        List<CompetitionDTO> competitionDTOs = competitions.stream()
                .map(mapper::toCompetitionDTO)
                .toList();
        return ResponseUtil.success(competitionDTOs, "Active competitions retrieved successfully");
    }

    @GetMapping("/types")
    public ResponseEntity<ApiResponse<List<String>>> getCompetitionTypes() {
        List<String> types = List.of(CompetitionType.values())
                .stream()
                .map(CompetitionType::name)
                .toList();
        return ResponseUtil.success(types, "Competition types retrieved successfully");
    }

    // Implement abstract methods from BaseCrudController
    @Override
    protected Page<Competition> getAllEntities(Pageable pageable) {
        return service.getAll(pageable);
    }

    @Override
    protected Competition getEntityById(Long id) {
        return service.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, "id", id));
    }

    @Override
    protected Competition createEntity(Competition entity) {
        return service.create(entity);
    }

    @Override
    protected Competition updateEntity(Long id, Competition entity) {
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
    protected CompetitionDTO toDTO(Competition entity) {
        return mapper.toCompetitionDTO(entity);
    }

    @Override
    protected Competition toEntity(CompetitionDTO dto) {
        return mapper.toCompetition(dto);
    }
}
