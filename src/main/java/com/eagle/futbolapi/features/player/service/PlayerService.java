package com.eagle.futbolapi.features.player.service;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.player.entity.Player;

@Service
@Transactional
public class PlayerService extends BaseCrudService<Player, Long> {

}
