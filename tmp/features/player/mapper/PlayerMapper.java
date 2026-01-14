package com.eagle.futbolapi.features.player.mapper;

import org.springframework.stereotype.Component;

import com.eagle.futbolapi.features.person.repository.PersonRepository;
import com.eagle.futbolapi.features.player.dto.PlayerDTO;
import com.eagle.futbolapi.features.player.entity.Player;
import com.eagle.futbolapi.features.player.entity.Position;
import com.eagle.futbolapi.features.player.entity.PreferredFoot;
import com.eagle.futbolapi.features.team.repository.TeamRepository;

@Component
public class PlayerMapper {

    private final PersonRepository personRepository;
    private final TeamRepository teamRepository;

    public PlayerMapper(PersonRepository personRepository, TeamRepository teamRepository) {
        this.personRepository = personRepository;
        this.teamRepository = teamRepository;
    }

    public PlayerDTO toPlayerDTO(Player player) {
        if (player == null) {
            return null;
        }

        return PlayerDTO.builder()
                .id(player.getId())
                .personId(player.getPerson() != null ? player.getPerson().getId() : null)
                .personDisplayName(player.getPerson() != null ? player.getPerson().getDisplayName() : null)
                .position(player.getPosition() != null ? player.getPosition().name() : null)
                .preferredFoot(player.getPreferredFoot() != null ? player.getPreferredFoot().name() : null)
                .currentTeamId(player.getCurrentTeam() != null ? player.getCurrentTeam().getId() : null)
                .currentTeamDisplayName(
                        player.getCurrentTeam() != null ? player.getCurrentTeam().getDisplayName() : null)
                .active(player.getActive())
                .createdAt(player.getCreatedAt())
                .createdBy(player.getCreatedBy())
                .updatedAt(player.getUpdatedAt())
                .updatedBy(player.getUpdatedBy())
                .build();
    }

    public Player toPlayer(PlayerDTO dto) {
        if (dto == null) {
            return null;
        }

        var builder = Player.builder()
                .id(dto.getId())
                .active(dto.getActive())
                .createdAt(dto.getCreatedAt())
                .createdBy(dto.getCreatedBy())
                .updatedAt(dto.getUpdatedAt())
                .updatedBy(dto.getUpdatedBy());

        if (dto.getPersonId() != null) {
            var person = personRepository.findById(dto.getPersonId())
                    .orElseThrow(() -> new IllegalArgumentException("Person not found with id: " + dto.getPersonId()));
            builder.person(person);
        }

        if (dto.getPosition() != null) {
            try {
                builder.position(Position.valueOf(dto.getPosition().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid position: " + dto.getPosition());
            }
        }

        if (dto.getPreferredFoot() != null) {
            try {
                builder.preferredFoot(PreferredFoot.valueOf(dto.getPreferredFoot().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid preferred foot: " + dto.getPreferredFoot());
            }
        }

        if (dto.getCurrentTeamId() != null) {
            var team = teamRepository.findById(dto.getCurrentTeamId())
                    .orElse(null);
            builder.currentTeam(team);
        }

        return builder.build();
    }
}
