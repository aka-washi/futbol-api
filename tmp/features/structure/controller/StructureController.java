package com.eagle.futbolapi.features.structure.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.shared.controller.BaseCrudController;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.structure.dto.StructureDTO;
import com.eagle.futbolapi.features.structure.entity.Structure;
import com.eagle.futbolapi.features.structure.mapper.StructureMapper;
import com.eagle.futbolapi.features.structure.service.StructureService;

@RestController
@RequestMapping("/structures")
@Validated
public class StructureController
        extends BaseCrudController<Structure, StructureDTO, StructureService, StructureMapper> {

    public StructureController(StructureService structureService, StructureMapper structureMapper) {
        super(
                structureService,
                structureMapper,
                "Structure",
                "Structure retrieved successfully",
                "Structure already exists",
                "SERVER_ERROR");
    }

    // Implement abstract methods from BaseCrudController
    @Override
    protected Page<Structure> getAllEntities(Pageable pageable) {
        return service.getAll(pageable);
    }

    @Override
    protected Structure getEntityById(Long id) {
        return service.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, "id", id));
    }

    @Override
    protected Structure createEntity(Structure entity) {
        return service.create(entity);
    }

    @Override
    protected Structure updateEntity(Long id, Structure entity) {
        return service.update(id, entity);
    }

    @Override
    protected void deleteEntity(Long id) {
        service.delete(id);
    }

    @Override
    protected boolean existsById(Long id) {
        return service.existsById(id);
    }

    @Override
    protected StructureDTO toDTO(Structure entity) {
        return mapper.toStructureDTO(entity);
    }

    @Override
    protected Structure toEntity(StructureDTO dto) {
        return mapper.toStructure(dto);
    }
}
