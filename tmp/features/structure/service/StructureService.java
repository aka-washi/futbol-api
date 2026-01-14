package com.eagle.futbolapi.features.structure.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.shared.service.BaseCrudService;
import com.eagle.futbolapi.features.structure.entity.Structure;
import com.eagle.futbolapi.features.structure.repository.StructureRepository;

@Service
@Transactional
public class StructureService extends BaseCrudService<Structure, Long> {

    private final StructureRepository structureRepository;

    public StructureService(StructureRepository structureRepository) {
        super(structureRepository);
        this.structureRepository = structureRepository;
    }

    @Override
    public Structure update(Long id, Structure structure) {
        Structure existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entity with given ID does not exist"));
        structure.setCreatedAt(existing.getCreatedAt());
        structure.setId(id);
        return super.update(id, structure);
    }

    @Override
    protected boolean isDuplicate(@NotNull Structure structure) {
        Objects.requireNonNull(structure, "Structure cannot be null");

        // For now, no strict duplicate check - business logic specific
        return false;
    }

    @Override
    protected boolean isDuplicate(Long id, @NotNull Structure structure) {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(structure, "Structure details cannot be null");

        Structure existingStructure = getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Structure", "id", id));

        // For updates, no strict duplicate check - business logic specific
        return false;
    }
}
