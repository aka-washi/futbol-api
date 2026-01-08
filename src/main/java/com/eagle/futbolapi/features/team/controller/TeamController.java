package com.eagle.futbolapi.features.team.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.shared.controller.BaseCrudController;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.team.dto.TeamDTO;
import com.eagle.futbolapi.features.team.entity.Team;
import com.eagle.futbolapi.features.team.mapper.TeamMapper;
import com.eagle.futbolapi.features.team.service.TeamService;

@RestController
@RequestMapping("/teams")
@Validated
public class TeamController extends BaseCrudController<Team, TeamDTO, TeamService, TeamMapper> {

    public TeamController(TeamService teamService, TeamMapper teamMapper) {
        super(
                teamService,
                teamMapper,
                "Team",
                "Team retrieved successfully",
                "Team already exists",
                "SERVER_ERROR");
    }

    // Implement abstract methods from BaseCrudController
    @Override
    protected Page<Team> getAllEntities(Pageable pageable) {
        return service.getAll(pageable);
    }

    @Override
    protected Team getEntityById(Long id) {
        return service.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, "id", id));
    }

    @Override
    protected Team createEntity(Team entity) {
        return service.create(entity);
    }

    @Override
    protected Team updateEntity(Long id, Team entity) {
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
    protected TeamDTO toDTO(Team entity) {
        return mapper.toTeamDTO(entity);
    }

    @Override
    protected Team toEntity(TeamDTO dto) {
        return mapper.toTeam(dto);
    }
}
