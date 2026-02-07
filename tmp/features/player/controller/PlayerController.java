package com.eagle.futbolapi.features.player.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.player.dto.PlayerDTO;
import com.eagle.futbolapi.features.player.entity.Player;
import com.eagle.futbolapi.features.player.mapper.PlayerMapper;
import com.eagle.futbolapi.features.player.service.PlayerService;

@Validated
@RestController
@RequestMapping("/players")
public class PlayerController extends BaseCrudController<Player, PlayerDTO, PlayerService, PlayerMapper> {

  private static final String RESOURCE_NAME = "Player";
  private static final String SUCCESS_MESSAGE = "Player(s) retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Player already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  public PlayerController(PlayerService playerService, PlayerMapper playerMapper) {
    super(
        playerService,
        playerMapper,
        RESOURCE_NAME,
        SUCCESS_MESSAGE,
        DUPLICATE_MESSAGE,
        SERVER_ERROR);
  }

}
