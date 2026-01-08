package com.eagle.futbolapi.features.tournament.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.shared.controller.BaseCrudController;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.tournament.dto.TournamentDTO;
import com.eagle.futbolapi.features.tournament.entity.Tournament;
import com.eagle.futbolapi.features.tournament.mapper.TournamentMapper;
import com.eagle.futbolapi.features.tournament.service.TournamentService;

@RestController
@RequestMapping("/tournaments")
@Validated
public class TournamentController
        extends BaseCrudController<Tournament, TournamentDTO, TournamentService, TournamentMapper> {

    public TournamentController(TournamentService tournamentService, TournamentMapper tournamentMapper) {
        super(
                tournamentService,
                tournamentMapper,
                "Tournament",
                "Tournament retrieved successfully",
                "Tournament already exists",
                "SERVER_ERROR");
    }

    // Implement abstract methods from BaseCrudController
    @Override
    protected Page<Tournament> getAllEntities(Pageable pageable) {
        return service.getAll(pageable);
    }

    @Override
    protected Tournament getEntityById(Long id) {
        return service.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, "id", id));
    }

    @Override
    protected Tournament createEntity(Tournament entity) {
        return service.create(entity);
    }

    @Override
    protected Tournament updateEntity(Long id, Tournament entity) {
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
    protected TournamentDTO toDTO(Tournament entity) {
        return mapper.toTournamentDTO(entity);
    }

    @Override
    protected Tournament toEntity(TournamentDTO dto) {
        return mapper.toTournament(dto);
    }
}
