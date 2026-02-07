package com.eagle.futbolapi.features.player.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.player.entity.Player;

@Repository
public interface PlayerRepository extends BaseRepository<Player, Long> {

  Optional<Player> findByPersonId(Long personId);

  Optional<Player> findByPersonDisplayName(String personDisplayName);

  boolean existsByPersonId(Long personId);

  @Query("SELECT COUNT(p) > 0 FROM Player p WHERE p.person.id = :personId AND p.id != :id")
  boolean existsByPersonIdAndIdNot(@Param("personId") Long personId, @Param("id") Long id);

}
