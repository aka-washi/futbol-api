package com.eagle.futbolapi.features.player.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.player.dto.PlayerDTO;
import com.eagle.futbolapi.features.player.entity.Player;
import com.eagle.futbolapi.features.player.mapper.PlayerMapper;
import com.eagle.futbolapi.features.player.service.PlayerService;
import com.eagle.futbolapi.features.shared.controller.BaseCrudController;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/players")
@Validated
public class PlayerController extends BaseCrudController<Player, PlayerDTO, PlayerService, PlayerMapper> {

    public PlayerController(PlayerService playerService, PlayerMapper playerMapper) {
        super(
                playerService,
                playerMapper,
                "Player",
                "Player retrieved successfully",
                "Player already exists",
                "SERVER_ERROR");
    }

    // Implement abstract methods from BaseCrudController
    @Override
    protected Page<Player> getAllEntities(Pageable pageable) {
        return service.getAll(pageable);
    }

    @Override
    protected Player getEntityById(Long id) {
        return service.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, "id", id));
    }

    @Override
    protected Player createEntity(Player entity) {
        return service.create(entity);
    }

    @Override
    protected Player updateEntity(Long id, Player entity) {
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
    protected PlayerDTO toDTO(Player entity) {
        return mapper.toPlayerDTO(entity);
    }

    @Override
    protected Player toEntity(PlayerDTO dto) {
        return mapper.toPlayer(dto);
    }
}
