package com.eagle.futbolapi.features.stage.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.shared.service.BaseCrudService;
import com.eagle.futbolapi.features.stage.entity.Stage;
import com.eagle.futbolapi.features.stage.repository.StageRepository;

@Service
@Transactional
public class StageService extends BaseCrudService<Stage, Long> {

    private final StageRepository stageRepository;

    public StageService(StageRepository stageRepository) {
        super(stageRepository);
        this.stageRepository = stageRepository;
    }

    public Optional<Stage> getStageByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Stage name cannot be null or empty");
        }
        return stageRepository.findByName(name);
    }

    public Optional<Stage> getStageByDisplayName(String displayName) {
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Stage display name cannot be null or empty");
        }
        return stageRepository.findByDisplayName(displayName);
    }

    @Override
    public Stage update(Long id, Stage stage) {
        Stage existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entity with given ID does not exist"));
        stage.setCreatedAt(existing.getCreatedAt());
        stage.setId(id);
        return super.update(id, stage);
    }

    @Override
    protected boolean isDuplicate(@NotNull Stage stage) {
        Objects.requireNonNull(stage, "Stage cannot be null");

        // For now, no strict duplicate check - business logic specific
        return false;
    }

    @Override
    protected boolean isDuplicate(Long id, @NotNull Stage stage) {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(stage, "Stage details cannot be null");

        Stage existingStage = getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stage", "id", id));

        // For updates, no strict duplicate check - business logic specific
        return false;
    }
}
