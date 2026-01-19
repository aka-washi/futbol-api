package com.eagle.futbolapi.features.structure.controller;

import jakarta.validation.constraints.Min;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.base.dto.ApiResponse;
import com.eagle.futbolapi.features.base.entity.StructureType;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.util.ResponseUtil;
import com.eagle.futbolapi.features.structure.dto.StructureDTO;
import com.eagle.futbolapi.features.structure.entity.Structure;
import com.eagle.futbolapi.features.structure.mapper.StructureMapper;
import com.eagle.futbolapi.features.structure.service.StructureService;

@Validated
@RestController
@RequestMapping("/structures")
public class StructureController
    extends BaseCrudController<Structure, StructureDTO, StructureService, StructureMapper> {

  private static final String DEFAULT_PAGE = "0";
  private static final int MIN_DEFAULT_PAGE = 0;
  private static final String DEFAULT_PAGE_SIZE = "20";
  private static final int MIN_PAGE_SIZE = 1;
  private static final String DEFAULT_SORT_FIELD = "name";
  private static final String DEFAULT_SORT_DIRECTION = "asc";

  private static final String RESOURCE_NAME = "Structure";
  private static final String SUCCESS_MESSAGE = "Structure retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Structure already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  public StructureController(StructureService structureService, StructureMapper structureMapper) {
    super(
        structureService,
        structureMapper,
        RESOURCE_NAME,
        SUCCESS_MESSAGE,
        DUPLICATE_MESSAGE,
        SERVER_ERROR);
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<ApiResponse<StructureDTO>> getStructureByName(@PathVariable String name) {
    var structure = service.getStructureByName(name)
        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "name", name));
    StructureDTO structureDTO = mapper.toStructureDTO(structure);
    return ResponseUtil.success(structureDTO, SUCCESS_MESSAGE);
  }

  @GetMapping("/displayName/{displayName}")
  public ResponseEntity<ApiResponse<StructureDTO>> getStructureByDisplayName(@PathVariable String displayName) {
    var structure = service.getStructureByDisplayName(displayName)
        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "displayName", displayName));
    StructureDTO structureDTO = mapper.toStructureDTO(structure);
    return ResponseUtil.success(structureDTO, SUCCESS_MESSAGE);
  }

  @GetMapping("/type/{type}")
  public ResponseEntity<ApiResponse<Page<StructureDTO>>> getStructuresByType(
      @PathVariable String type,
      @RequestParam(value = "page", defaultValue = DEFAULT_PAGE) @Min(MIN_DEFAULT_PAGE) int page,
      @RequestParam(value = "size", defaultValue = DEFAULT_PAGE_SIZE) @Min(MIN_PAGE_SIZE) int size,
      @RequestParam(value = "sortField", defaultValue = DEFAULT_SORT_FIELD) String sortField,
      @RequestParam(value = "sortDirection", defaultValue = DEFAULT_SORT_DIRECTION) String sortDirection) {
    Pageable pageable = ResponseUtil.buildPageable(page, size, sortField, sortDirection);
    StructureType structureType;
    try {
      structureType = StructureType.valueOf(type.toUpperCase());
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(ApiResponse.error("INVALID_TYPE", "Invalid structure: " + type));
    }

    Page<Structure> structuresPage = service.getStructuresByType(structureType, pageable);
    Page<StructureDTO> structureDTOs = structuresPage.map(mapper::toStructureDTO);

    return ResponseUtil.successWithPagination(structureDTOs, SUCCESS_MESSAGE);
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
  protected Structure createEntity(StructureDTO dto) {
    return service.create(dto);
  }

  @Override
  protected Structure updateEntity(Long id, StructureDTO dto) {
    return service.update(id, dto);
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
