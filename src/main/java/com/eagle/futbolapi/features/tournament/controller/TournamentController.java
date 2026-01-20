package com.eagle.futbolapi.features.tournament.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.tournament.dto.TournamentDTO;
import com.eagle.futbolapi.features.tournament.entity.Tournament;
import com.eagle.futbolapi.features.tournament.mapper.TournamentMapper;
import com.eagle.futbolapi.features.tournament.service.TournamentService;

public class TournamentController extends BaseCrudController<Tournament, TournamentDTO, TournamentService, TournamentMapper> {

  private static final String RESOURCE_NAME = "Tournament";
  private static final String SUCCESS_MESSAGE = "Tournament retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Tournament already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

    public TournamentController(TournamentService tournamentService, TournamentMapper tournamentMapper) {
        super(
                tournamentService,
                tournamentMapper,
                RESOURCE_NAME,
                SUCCESS_MESSAGE,
                DUPLICATE_MESSAGE,
                SERVER_ERROR);
    }

    @GetMapping("/name/{name}")
    public TournamentDTO getTournamentByName(String name) {
        Tournament tournament = service.getTournamentByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, "name", name));
        return mapper.toTournamentDTO(tournament);
    }

    @GetMapping("/displayName/{displayName}")
    public TournamentDTO getTournamentByDisplayName(String displayName) {
        Tournament tournament = service.getTournamentByDisplayName(displayName)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, "displayName", displayName));
        return mapper.toTournamentDTO(tournament);
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
    protected Tournament createEntity(TournamentDTO dto) {
        return service.create(dto);
    }

    @Override
    protected Tournament updateEntity(Long id, TournamentDTO dto) {
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
    protected TournamentDTO toDTO(Tournament entity) {
        return mapper.toTournamentDTO(entity);
    }

    @Override
    protected Tournament toEntity(TournamentDTO dto) {
        return mapper.toTournament(dto);
    }
  }
