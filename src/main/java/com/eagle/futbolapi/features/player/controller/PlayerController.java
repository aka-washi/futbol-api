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
import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/players")
@Validated
public class PlayerController extends BaseCrudController<Player, PlayerDTO, PlayerService, PlayerMapper> {

}
