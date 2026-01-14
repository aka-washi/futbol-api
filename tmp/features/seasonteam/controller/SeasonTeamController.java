package com.eagle.futbolapi.features.seasonteam.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.seasonteam.dto.SeasonTeamDTO;
import com.eagle.futbolapi.features.seasonteam.entity.SeasonTeam;
import com.eagle.futbolapi.features.seasonteam.service.SeasonTeamService;
import com.eagle.futbolapi.features.shared.controller.BaseCrudController;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/season-teams")
@Validated
public class SeasonTeamController extends BaseCrudController<SeasonTeam, SeasonTeamDTO, SeasonTeamService, Object> {

    public SeasonTeamController(SeasonTeamService seasonTeamService) {
        super(
                seasonTeamService,
                null, // Mapper not available yet
                "SeasonTeam",
                "Season team retrieved successfully",
                "Season team already exists",
                "SERVER_ERROR");
    }

    // Implement abstract methods from BaseCrudController
    @Override
    protected Page<SeasonTeam> getAllEntities(Pageable pageable) {
        return service.getAll(pageable);
    }

    @Override
    protected SeasonTeam getEntityById(Long id) {
        return service.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, "id", id));
    }

    @Override
    protected SeasonTeam createEntity(SeasonTeam entity) {
        return service.create(entity);
    }

    @Override
    protected SeasonTeam updateEntity(Long id, SeasonTeam entity) {
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
    protected SeasonTeamDTO toDTO(SeasonTeam entity) {
        // Simple DTO conversion (would be better with mapper)
        return SeasonTeamDTO.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }

    @Override
    protected SeasonTeam toEntity(SeasonTeamDTO dto) {
        // Simple entity conversion (would be better with mapper)
        return SeasonTeam.builder()
                .id(dto.getId())
                .build();
    }
}
