package com.eagle.futbolapi.features.player.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.player.dto.PlayerDto;
import com.eagle.futbolapi.features.player.entity.Player;
import com.eagle.futbolapi.features.player.mapper.PlayerMapper;
import com.eagle.futbolapi.features.player.service.PlayerService;

import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for Player resources.
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/players")
public class PlayerController extends BaseCrudController<Player, PlayerDto, PlayerService, PlayerMapper> {

  private static final String RESOURCE_NAME = "Player";
  private static final String SUCCESS_MESSAGE = "Player retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Player already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  protected PlayerController(PlayerService service, PlayerMapper mapper) {
    super(service, mapper, RESOURCE_NAME, SUCCESS_MESSAGE, DUPLICATE_MESSAGE, SERVER_ERROR);
  }

}
