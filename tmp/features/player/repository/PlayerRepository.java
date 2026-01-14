package com.eagle.futbolapi.features.player.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.person.entity.Person;
import com.eagle.futbolapi.features.player.entity.Player;
import com.eagle.futbolapi.features.team.entity.Team;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    List<Player> findByPerson(Person person);

    List<Player> findByCurrentTeam(Team currentTeam);

    List<Player> findByCurrentTeamId(Long currentTeamId);

    @Query("SELECT p FROM Player p WHERE p.person.displayName ILIKE %:searchTerm%")
    List<Player> findByDisplayNameContainingIgnoreCase(@Param("searchTerm") String searchTerm);

    @Query("SELECT p FROM Player p WHERE p.person.displayName = :displayName")
    List<Player> findByDisplayName(@Param("displayName") String displayName);

}
