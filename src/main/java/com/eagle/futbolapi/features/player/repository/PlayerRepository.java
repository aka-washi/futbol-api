package com.eagle.futbolapi.features.player.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.player.entity.Player;

/**
 * Repository interface for Player entity data access operations.
 */
@Repository
public interface PlayerRepository extends BaseRepository<Player, Long> {

  Optional<Player> findByPersonDisplayName(String displayName);

}
