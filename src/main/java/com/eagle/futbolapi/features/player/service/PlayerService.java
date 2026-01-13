package com.eagle.futbolapi.features.player.service;

import java.util.List;
import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.person.entity.Person;
import com.eagle.futbolapi.features.player.entity.Player;
import com.eagle.futbolapi.features.player.repository.PlayerRepository;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.shared.service.BaseCrudService;
import com.eagle.futbolapi.features.team.entity.Team;

@Service
@Transactional
public class PlayerService extends BaseCrudService<Player, Long> {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        super(playerRepository);
        this.playerRepository = playerRepository;
    }

    public List<Player> searchPlayersByDisplayName(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            throw new IllegalArgumentException("Search term cannot be null or empty");
        }
        return playerRepository.findByDisplayNameContainingIgnoreCase(searchTerm);
    }

    public List<Player> getPlayersByDisplayName(String displayName) {
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Display name cannot be null or empty");
        }
        return playerRepository.findByDisplayName(displayName);
    }

    public List<Player> getPlayersByPerson(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Person cannot be null");
        }
        return playerRepository.findByPerson(person);
    }

    public List<Player> getPlayersByCurrentTeam(Team currentTeam) {
        if (currentTeam == null) {
            throw new IllegalArgumentException("Current team cannot be null");
        }
        return playerRepository.findByCurrentTeam(currentTeam);
    }

    public List<Player> getPlayersByCurrentTeamId(Long currentTeamId) {
        if (currentTeamId == null) {
            throw new IllegalArgumentException("Current team ID cannot be null");
        }
        return playerRepository.findByCurrentTeamId(currentTeamId);
    }

    @Override
    public Player update(Long id, Player player) {
        Player existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entity with given ID does not exist"));
        player.setCreatedAt(existing.getCreatedAt());
        player.setId(id);
        return super.update(id, player);
    }

    @Override
    protected boolean isDuplicate(@NotNull Player player) {
        Objects.requireNonNull(player, "Player cannot be null");

        // Players can have same names, jersey numbers can be repeated across teams
        // For now, no strict duplicate check - business logic specific
        return false;
    }

    @Override
    protected boolean isDuplicate(Long id, @NotNull Player player) {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(player, "Player details cannot be null");

        Player existingPlayer = getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player", "id", id));

        // For updates, no strict duplicate check - business logic specific
        return false;
    }
}
