package com.eagle.futbolapi.features.player.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.player.entity.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

}
