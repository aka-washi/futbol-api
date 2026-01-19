package com.eagle.futbolapi.features.structure.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.entity.StructureType;
import com.eagle.futbolapi.features.base.exception.NoChangesDetectedException;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.pointsystem.repository.PointSystemRepository;
import com.eagle.futbolapi.features.structure.dto.StructureDTO;
import com.eagle.futbolapi.features.structure.entity.Structure;
import com.eagle.futbolapi.features.structure.mapper.StructureMapper;
import com.eagle.futbolapi.features.structure.repository.StructureRepository;

@Service
@Transactional
public class StructureService extends BaseCrudService<Structure, Long, StructureDTO> {

  private final StructureRepository structureRepository;
  private final StructureMapper structureMapper;
  private final PointSystemRepository pointSystemRepository;

  public StructureService(
      StructureRepository structureRepository,
      StructureMapper structureMapper,
      PointSystemRepository pointSystemRepository) {
    super(structureRepository);
    this.structureRepository = structureRepository;
    this.structureMapper = structureMapper;
    this.pointSystemRepository = pointSystemRepository;
  }

  public Optional<Structure> getStructureByName(String name) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Structure name cannot be null or empty");
    }
    return structureRepository.findByName(name);
  }

  public Optional<Structure> getStructureByDisplayName(String displayName) {
    if (displayName == null || displayName.isEmpty()) {
      throw new IllegalArgumentException("Structure display name cannot be null or empty");
    }
    return structureRepository.findByDisplayName(displayName);
  }

  public Page<Structure> getStructuresByType(StructureType type, Pageable pageable) {
    if (pageable == null) {
      pageable = Pageable.unpaged();
    }
    if (type == null) {
      throw new IllegalArgumentException("Structure type cannot be null");
    }
    return structureRepository.findByType(type, pageable);
  }

  @Override
  protected Structure convertToEntity(StructureDTO dto) {
    return structureMapper.toStructure(dto);
  }

  @Override
  protected void resolveRelationships(StructureDTO dto, Structure structure) {
    // Map point system from display name or ID
    if (dto.getPointSystemDisplayName() != null && !dto.getPointSystemDisplayName().isEmpty()) {
      var pointSystem = pointSystemRepository.findByDisplayName(dto.getPointSystemDisplayName())
          .orElseThrow(() -> new ResourceNotFoundException("Point system with given display name does not exist"));
      structure.setPointSystem(pointSystem);
    } else if (dto.getPointSystemId() != null) {
      var pointSystem = pointSystemRepository.findById(dto.getPointSystemId())
          .orElseThrow(() -> new ResourceNotFoundException("Point system with given ID does not exist"));
      structure.setPointSystem(pointSystem);
    }
  }

  @Override
  public Structure update(Long id, StructureDTO dto) {
    // Get existing entity to preserve audit fields
    Structure existing = repository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Entity with given ID does not exist"));

    // Convert DTO to entity and resolve relationships
    Structure structure = convertToEntity(dto);
    resolveRelationships(dto, structure);

    // preserve audit fields from existing entity
    structure.setId(id);
    structure.setCreatedAt(existing.getCreatedAt());
    structure.setCreatedBy(existing.getCreatedBy());

    // Validate and save
    if (existing.equals(structure)) {
      throw new NoChangesDetectedException("No changes detected for entity", id);
    }

    if (isDuplicate(structure)) {
      throw new IllegalArgumentException("Duplicate entity detected");
    }

    return repository.save(structure);

  }

  @Override
  protected boolean isDuplicate(@NotNull Structure structure) {
    Objects.requireNonNull(structure, "Structure cannot be null");

    return structureRepository.existsByName(structure.getName())
        || structureRepository.existsByDisplayName(structure.getDisplayName());
  }

  @Override
  protected boolean isDuplicate(Long id, @NotNull Structure structure) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(structure, "Structure cannot be null");

    return structureRepository.existsByNameAndIdNot(structure.getName(), id)
        || structureRepository.existsByDisplayNameAndIdNot(structure.getDisplayName(), id);
  }

}
