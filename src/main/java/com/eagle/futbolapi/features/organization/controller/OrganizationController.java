package com.eagle.futbolapi.features.organization.controller;

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
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.util.ResponseUtil;
import com.eagle.futbolapi.features.organization.dto.OrganizationDTO;
import com.eagle.futbolapi.features.organization.entity.Organization;
import com.eagle.futbolapi.features.organization.entity.OrganizationType;
import com.eagle.futbolapi.features.organization.mapper.OrganizationMapper;
import com.eagle.futbolapi.features.organization.service.OrganizationService;

@Validated
@RestController
@RequestMapping("/organizations")
public class OrganizationController
    extends BaseCrudController<Organization, OrganizationDTO, OrganizationService, OrganizationMapper> {

  private static final String DEFAULT_PAGE = "0";
  private static final int MIN_DEFAULT_PAGE = 0;
  private static final String DEFAULT_PAGE_SIZE = "20";
  private static final int MIN_PAGE_SIZE = 1;
  private static final String DEFAULT_SORT_FIELD = "name";
  private static final String DEFAULT_SORT_DIRECTION = "asc";

  private static final String RESOURCE_NAME = "Organization";
  private static final String SUCCESS_MESSAGE = "Organization(s) retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Organization already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  public OrganizationController(OrganizationService organizationService, OrganizationMapper organizationMapper) {
    super(
        organizationService,
        organizationMapper,
        RESOURCE_NAME,
        SUCCESS_MESSAGE,
        DUPLICATE_MESSAGE,
        SERVER_ERROR);
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<ApiResponse<OrganizationDTO>> getOrganizationByName(@PathVariable String name) {
    var organization = service.getOrganizationByName(name)
        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "name", name));
    OrganizationDTO organizationDTO = mapper.toOrganizationDTO(organization);
    return ResponseUtil.success(organizationDTO, SUCCESS_MESSAGE);
  }

  @GetMapping("/displayName/{displayName}")
  public ResponseEntity<ApiResponse<OrganizationDTO>> getOrganizationByDisplayName(@PathVariable String displayName) {
    var organization = service.getOrganizationByDisplayName(displayName)
        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "displayName", displayName));
    OrganizationDTO organizationDTO = mapper.toOrganizationDTO(organization);
    return ResponseUtil.success(organizationDTO, SUCCESS_MESSAGE);
  }

  @GetMapping("/abbreviation/{abbreviation}")
  public ResponseEntity<ApiResponse<OrganizationDTO>> getOrganizationByAbbreviation(@PathVariable String abbreviation) {
    var organization = service.getOrganizationByAbbreviation(abbreviation)
        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "abbreviation", abbreviation));
    OrganizationDTO organizationDTO = mapper.toOrganizationDTO(organization);
    return ResponseUtil.success(organizationDTO, SUCCESS_MESSAGE);
  }

  @GetMapping("/type/{type}")
  public ResponseEntity<ApiResponse<Page<OrganizationDTO>>> getOrganizationsByType(
      @PathVariable String type,
      @RequestParam(defaultValue = DEFAULT_PAGE) @Min(MIN_DEFAULT_PAGE) int page,
      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) @Min(MIN_PAGE_SIZE) int size,
      @RequestParam(defaultValue = DEFAULT_SORT_FIELD) String sortField,
      @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION) String sortDirection) {

    Pageable pageable = ResponseUtil.buildPageable(page, size, sortField, sortDirection);

    OrganizationType organizationType;
    try {
      organizationType = OrganizationType.valueOf(type.toUpperCase());
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(ApiResponse.error("INVALID_TYPE", "Invalid organization type: " + type));
    }

    Page<Organization> organizationPage = service.getOrganizationsByType(organizationType, pageable);
    Page<OrganizationDTO> organizations = organizationPage.map(mapper::toOrganizationDTO);

    return ResponseUtil.successWithPagination(organizations, SUCCESS_MESSAGE);
  }

  @Override
  protected Page<Organization> getAllEntities(Pageable pageable) {
    return service.getAll(pageable);
  }

  @Override
  protected Organization getEntityById(Long id) {
    return service.getById(id)
        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "id", id));
  }

  @Override
  protected Organization createEntity(OrganizationDTO dto) {
    return service.create(dto);
  }

  @Override
  protected Organization updateEntity(Long id, OrganizationDTO dto) {
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
  protected OrganizationDTO toDTO(Organization entity) {
    return mapper.toOrganizationDTO(entity);
  }

  @Override
  protected Organization toEntity(OrganizationDTO dto) {
    return mapper.toOrganization(dto);
  }

}
