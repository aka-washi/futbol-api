package com.eagle.futbolapi.features.tournament.mapper;

import org.springframework.stereotype.Component;

import com.eagle.futbolapi.features.organization.repository.OrganizationRepository;
import com.eagle.futbolapi.features.tournament.dto.TournamentDTO;
import com.eagle.futbolapi.features.tournament.entity.Category;
import com.eagle.futbolapi.features.tournament.entity.Tournament;
import com.eagle.futbolapi.features.tournament.entity.TournamentType;
import com.eagle.futbolapi.features.tournament.repository.TournamentRepository;

@Component
public class TournamentMapper {

    private final OrganizationRepository organizationRepository;
    private final TournamentRepository tournamentRepository;

    public TournamentMapper(OrganizationRepository organizationRepository, TournamentRepository tournamentRepository) {
        this.organizationRepository = organizationRepository;
        this.tournamentRepository = tournamentRepository;
    }

    public TournamentDTO toTournamentDTO(Tournament tournament) {
        if (tournament == null) {
            return null;
        }

        return TournamentDTO.builder()
                .id(tournament.getId())
                .organizationId(tournament.getOrganization() != null ? tournament.getOrganization().getId() : null)
                .organizationDisplayName(
                        tournament.getOrganization() != null ? tournament.getOrganization().getDisplayName() : null)
                .name(tournament.getName())
                .displayName(tournament.getDisplayName())
                .type(tournament.getType() != null ? tournament.getType().name() : null)
                .category(tournament.getCategory() != null ? tournament.getCategory().name() : null)
                .level(tournament.getLevel())
                .relegationToId(tournament.getRelegationTo() != null ? tournament.getRelegationTo().getId() : null)
                .relegationToDisplayName(
                        tournament.getRelegationTo() != null ? tournament.getRelegationTo().getDisplayName() : null)
                .description(tournament.getDescription())
                .logo(tournament.getLogo())
                .foundedYear(tournament.getFoundedYear())
                .active(tournament.getActive())
                .createdAt(tournament.getCreatedAt())
                .createdBy(tournament.getCreatedBy())
                .updatedAt(tournament.getUpdatedAt())
                .updatedBy(tournament.getUpdatedBy())
                .build();
    }

    public Tournament toTournament(TournamentDTO dto) {
        if (dto == null) {
            return null;
        }

        var builder = Tournament.builder()
                .id(dto.getId())
                .name(dto.getName())
                .displayName(dto.getDisplayName())
                .level(dto.getLevel())
                .description(dto.getDescription())
                .logo(dto.getLogo())
                .foundedYear(dto.getFoundedYear())
                .active(dto.getActive())
                .createdAt(dto.getCreatedAt())
                .createdBy(dto.getCreatedBy())
                .updatedAt(dto.getUpdatedAt())
                .updatedBy(dto.getUpdatedBy());

        if (dto.getType() != null) {
            try {
                builder.type(TournamentType.valueOf(dto.getType().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid tournament type: " + dto.getType());
            }
        }

        if (dto.getCategory() != null) {
            try {
                builder.category(Category.valueOf(dto.getCategory().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid category: " + dto.getCategory());
            }
        }

        if (dto.getOrganizationId() != null) {
            var organization = organizationRepository.findById(dto.getOrganizationId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Organization not found with id: " + dto.getOrganizationId()));
            builder.organization(organization);
        }

        if (dto.getRelegationToId() != null) {
            var relegationTo = tournamentRepository.findById(dto.getRelegationToId())
                    .orElse(null);
            builder.relegationTo(relegationTo);
        }

        return builder.build();
    }
}
