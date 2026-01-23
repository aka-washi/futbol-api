package com.eagle.futbolapi.features.player.service;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.player.dto.PlayerDTO;
import com.eagle.futbolapi.features.player.entity.Player;

@Service
@Transactional
public class PlayerService extends BaseCrudService<Player, Long, PlayerDTO> {

  protected PlayerService(JpaRepository<Player, Long> repository, BaseMapper<Player, PlayerDTO> mapper) {
    super(repository, mapper);
    // TODO Auto-generated constructor stub
  }

  @Override
  protected boolean isDuplicate(@NotNull Player entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isDuplicate'");
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Player entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isDuplicate'");
  }

}
