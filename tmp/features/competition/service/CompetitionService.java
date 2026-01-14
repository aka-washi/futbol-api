package com.eagle.futbolapi.features.competition.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.competition.entity.Competition;
import com.eagle.futbolapi.features.competition.entity.CompetitionType;
import com.eagle.futbolapi.features.competition.repository.CompetitionRepository;
import com.eagle.futbolapi.features.shared.exception.DuplicateResourceException;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.shared.service.BaseCrudService;

@Service
@Transactional
public class CompetitionService extends BaseCrudService<Competition, Long> {

    private final CompetitionRepository competitionRepository;

    public CompetitionService(CompetitionRepository competitionRepository) {
        super(competitionRepository);
        this.competitionRepository = competitionRepository;
    }

    public Optional<Competition> getCompetitionByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Competition name cannot be null or empty");
        }
        return competitionRepository.findByName(name);
    }

    public Optional<Competition> getCompetitionByDisplayName(String displayName) {
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Competition display name cannot be null or empty");
        }
        return competitionRepository.findByDisplayName(displayName);
    }

    public List<Competition> getCompetitionsByType(CompetitionType type) {
        if (type == null) {
            throw new IllegalArgumentException("Competition type cannot be null");
        }
        return competitionRepository.findByType(type);
    }

    public List<Competition> getCompetitionsBySeason(Long seasonId) {
        if (seasonId == null) {
            throw new IllegalArgumentException("Season ID cannot be null");
        }
        return competitionRepository.findBySeasonId(seasonId);
    }

    public List<Competition> getActiveCompetitions() {
        return competitionRepository.findByActive(true);
    }

    public List<Competition> getInactiveCompetitions() {
        return competitionRepository.findByActive(false);
    }

    public List<Competition> getCompetitionsBySeasonAndType(Long seasonId, CompetitionType type) {
        if (seasonId == null) {
            throw new IllegalArgumentException("Season ID cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("Competition type cannot be null");
        }
        return competitionRepository.findBySeasonIdAndType(seasonId, type);
    }

    public List<Competition> getActiveCompetitionsBySeason(Long seasonId) {
        if (seasonId == null) {
            throw new IllegalArgumentException("Season ID cannot be null");
        }
        return competitionRepository.findBySeasonIdAndActive(seasonId, true);
    }

    @Override
    protected boolean isDuplicate(Competition competition) {
        if (competition == null) {
            return false;
        }

        // Check for duplicate name in the same season
        if (competition.getSeason() != null && competition.getName() != null) {
            boolean nameExists = competitionRepository.existsBySeasonIdAndName(
                competition.getSeason().getId(),
                competition.getName()
            );
            if (nameExists) {
                return true;
            }
        }

        // Check for duplicate display name in the same season
        if (competition.getSeason() != null && competition.getDisplayName() != null) {
            boolean displayNameExists = competitionRepository.existsBySeasonIdAndDisplayName(
                competition.getSeason().getId(),
                competition.getDisplayName()
            );
            if (displayNameExists) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected boolean isDuplicate(Long id, Competition competition) {
        if (competition == null || id == null) {
            return false;
        }

        // Get existing competition to compare
        Optional<Competition> existingOpt = getById(id);
        if (existingOpt.isEmpty()) {
            return false;
        }

        // Check for duplicate name in the same season (excluding current entity)
        if (competition.getSeason() != null && competition.getName() != null) {
            List<Competition> competitionsWithSameName = competitionRepository.findBySeasonId(competition.getSeason().getId())
                .stream()
                .filter(c -> c.getName().equals(competition.getName()) && !c.getId().equals(id))
                .toList();
            if (!competitionsWithSameName.isEmpty()) {
                return true;
            }
        }

        // Check for duplicate display name in the same season (excluding current entity)
        if (competition.getSeason() != null && competition.getDisplayName() != null) {
            List<Competition> competitionsWithSameDisplayName = competitionRepository.findBySeasonId(competition.getSeason().getId())
                .stream()
                .filter(c -> c.getDisplayName().equals(competition.getDisplayName()) && !c.getId().equals(id))
                .toList();
            if (!competitionsWithSameDisplayName.isEmpty()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Competition create(Competition competition) {
        validateCompetition(competition);

        if (isDuplicate(competition)) {
            throw new DuplicateResourceException("Competition already exists with the same name or display name in this season");
        }

        return super.create(competition);
    }

    @Override
    public Competition update(Long id, Competition competition) {
        validateCompetition(competition);

        // Get existing competition to compare
        Competition existing = getById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Competition", "id", id.toString()));

        // Check for duplicates only if name or display name changed
        if ((!Objects.equals(existing.getName(), competition.getName()) ||
             !Objects.equals(existing.getDisplayName(), competition.getDisplayName())) &&
             isDuplicate(id, competition)) {
            throw new DuplicateResourceException("Competition already exists with the same name or display name in this season");
        }

        return super.update(id, competition);
    }

    private void validateCompetition(Competition competition) {
        if (competition == null) {
            throw new IllegalArgumentException("Competition cannot be null");
        }
        if (competition.getName() == null || competition.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Competition name is required");
        }
        if (competition.getDisplayName() == null || competition.getDisplayName().trim().isEmpty()) {
            throw new IllegalArgumentException("Competition display name is required");
        }
        if (competition.getType() == null) {
            throw new IllegalArgumentException("Competition type is required");
        }
        if (competition.getStartDate() == null) {
            throw new IllegalArgumentException("Competition start date is required");
        }
        if (competition.getEndDate() == null) {
            throw new IllegalArgumentException("Competition end date is required");
        }
        if (competition.getStartDate().isAfter(competition.getEndDate())) {
            throw new IllegalArgumentException("Competition start date must be before or equal to end date");
        }
        if (competition.getSeason() == null) {
            throw new IllegalArgumentException("Competition season is required");
        }
    }

    @Override
    protected boolean entitiesEqual(Competition existing, Competition updated) {
        if (existing == null || updated == null) {
            return false;
        }

        return Objects.equals(existing.getName(), updated.getName()) &&
               Objects.equals(existing.getDisplayName(), updated.getDisplayName()) &&
               Objects.equals(existing.getType(), updated.getType()) &&
               Objects.equals(existing.getStartDate(), updated.getStartDate()) &&
               Objects.equals(existing.getEndDate(), updated.getEndDate()) &&
               Objects.equals(existing.getActive(), updated.getActive()) &&
               Objects.equals(existing.getTotalMatchdays(), updated.getTotalMatchdays()) &&
               Objects.equals(existing.getDescription(), updated.getDescription()) &&
               Objects.equals(existing.getSeason(), updated.getSeason());
    }
}
