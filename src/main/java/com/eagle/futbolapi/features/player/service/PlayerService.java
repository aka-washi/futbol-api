package com.eagle.futbolapi.features.player.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.player.entity.Player;
import com.eagle.futbolapi.features.player.repository.PlayerRepository;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.shared.service.BaseCrudService;

@Service
@Transactional
public class PlayerService extends BaseCrudService<Player, Long> {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        super(playerRepository);
        this.playerRepository = playerRepository;
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
