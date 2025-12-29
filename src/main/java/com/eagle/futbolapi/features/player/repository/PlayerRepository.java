package com.eagle.futbolapi.features.player.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eagle.futbolapi.features.player.entity.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {

}
