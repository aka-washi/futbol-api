package com.eagle.futbolapi.features.player.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.player.entity.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

  boolean existsByPersonId(Long personId);

  @Query("SELECT COUNT(p) > 0 FROM Player p WHERE p.person.id = :personId AND p.id != :id")
  boolean existsByPersonIdAndIdNot(@Param("personId") Long personId, @Param("id") Long id);

}
