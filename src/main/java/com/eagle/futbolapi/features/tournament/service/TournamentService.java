package com.eagle.futbolapi.features.tournament.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.shared.service.BaseCrudService;
import com.eagle.futbolapi.features.tournament.entity.Tournament;
import com.eagle.futbolapi.features.tournament.repository.TournamentRepository;

@Service
@Transactional
public class TournamentService extends BaseCrudService<Tournament, Long> {

    private final TournamentRepository tournamentRepository;

    public TournamentService(TournamentRepository tournamentRepository) {
        super(tournamentRepository);
        this.tournamentRepository = tournamentRepository;
    }

    @Override
    public Tournament update(Long id, Tournament tournament) {
        Tournament existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entity with given ID does not exist"));
        tournament.setCreatedAt(existing.getCreatedAt());
        tournament.setId(id);
        return super.update(id, tournament);
    }

    @Override
    protected boolean isDuplicate(@NotNull Tournament tournament) {
        Objects.requireNonNull(tournament, "Tournament cannot be null");

        // For now, no strict duplicate check - business logic specific
        return false;
    }

    @Override
    protected boolean isDuplicate(Long id, @NotNull Tournament tournament) {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(tournament, "Tournament details cannot be null");

        Tournament existingTournament = getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tournament", "id", id));

        // For updates, no strict duplicate check - business logic specific
        return false;
    }
}
