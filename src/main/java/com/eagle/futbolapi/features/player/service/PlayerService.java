package com.eagle.futbolapi.features.player.service;

import java.util.List;
import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.person.entity.Person;
import com.eagle.futbolapi.features.player.entity.Player;
import com.eagle.futbolapi.features.player.repository.PlayerRepository;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.team.entity.Team;

@Service
@Transactional
public class PlayerService extends BaseCrudService<Player, Long> {

}
