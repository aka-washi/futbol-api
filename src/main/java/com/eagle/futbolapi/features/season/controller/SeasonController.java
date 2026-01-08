package com.eagle.futbolapi.features.season.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.season.dto.SeasonDTO;
import com.eagle.futbolapi.features.season.entity.Season;
import com.eagle.futbolapi.features.season.mapper.SeasonMapper;
import com.eagle.futbolapi.features.season.service.SeasonService;
import com.eagle.futbolapi.features.shared.controller.BaseCrudController;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/seasons")
@Validated
public class SeasonController extends BaseCrudController<Season, SeasonDTO, SeasonService, SeasonMapper> {

    public SeasonController(SeasonService seasonService, SeasonMapper seasonMapper) {
        super(
                seasonService,
                seasonMapper,
                "Season",
                "Season retrieved successfully",
                "Season already exists",
                "SERVER_ERROR");
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
    protected Season createEntity(Season entity) {
        return service.create(entity);
    }

    @Override
    protected Season updateEntity(Long id, Season entity) {
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
    protected SeasonDTO toDTO(Season entity) {
        return mapper.toSeasonDTO(entity);
    }

    @Override
    protected Season toEntity(SeasonDTO dto) {
        return mapper.toSeason(dto);
    }
}
