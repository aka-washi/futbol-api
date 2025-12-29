package com.eagle.futbolapi.features.organization.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.organization.dto.OrganizationDTO;
import com.eagle.futbolapi.features.organization.entity.Organization;
import com.eagle.futbolapi.features.organization.entity.OrganizationType;
import com.eagle.futbolapi.features.organization.mapper.OrganizationMapper;
import com.eagle.futbolapi.features.organization.service.OrganizationService;
import com.eagle.futbolapi.features.shared.ApiResponse;
import com.eagle.futbolapi.features.shared.ResponseUtil;
import com.eagle.futbolapi.features.shared.exception.DuplicateResourceException;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/organizations")
@RequiredArgsConstructor
@Validated
public class OrganizationController {

    private static final String DEFAULT_SORT_FIELD = "name";
    private static final String DEFAULT_SORT_DIRECTION = "asc";
    private static final String DEFAULT_PAGE = "0";
    private static final int MIN_DEFAULT_PAGE = 0;
    private static final String DEFAULT_PAGE_SIZE = "20";
    private static final int MIN_PAGE_SIZE = 1;
    private static final String RESOURCE_NAME = "Organization";
    private static final String SUCCESS_MESSAGE = "Organization(s) retrieved successfully";
    private static final String SERVER_ERROR = "SERVER_ERROR";

    private final OrganizationService organizationService;
    private final OrganizationMapper organizationMapper;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<OrganizationDTO>>> getAllOrganizations(
            @RequestParam(defaultValue = DEFAULT_PAGE) @Min(MIN_DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) @Min(MIN_PAGE_SIZE) int size,
            @RequestParam(defaultValue = DEFAULT_SORT_FIELD) String sortBy,
            @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION) String sortDir) {

        Pageable pageable = ResponseUtil.buildPageable(page, size, sortBy, sortDir);
        Page<Organization> organizationPage = organizationService.getAllOrganizations(pageable);

        Page<OrganizationDTO> organizations = organizationPage.map(organizationMapper::toOrganizationDTO);
        return ResponseUtil.successWithPagination(organizations, SUCCESS_MESSAGE);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrganizationDTO>> getOrganizationById(@PathVariable Long id) {
        var organization = organizationService.getOrganizationById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "id", id));
        OrganizationDTO organizationDTO = organizationMapper.toOrganizationDTO(organization);
        return ResponseUtil.success(organizationDTO, SUCCESS_MESSAGE);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse<OrganizationDTO>> getOrganizationByName(@PathVariable String name) {
        var organization = organizationService.getOrganizationByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "name", name));
        OrganizationDTO organizationDTO = organizationMapper.toOrganizationDTO(organization);
        return ResponseUtil.success(organizationDTO, SUCCESS_MESSAGE);
    }

    @GetMapping("/displayName/{displayName}")
    public ResponseEntity<ApiResponse<OrganizationDTO>> getOrganizationByDisplayName(@PathVariable String displayName) {
        var organization = organizationService.getOrganizationByDisplayName(displayName)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "displayName", displayName));
        OrganizationDTO organizationDTO = organizationMapper.toOrganizationDTO(organization);
        return ResponseUtil.success(organizationDTO, SUCCESS_MESSAGE);
    }

    @GetMapping("abbreviation/{abbreviation}")
    public ResponseEntity<ApiResponse<OrganizationDTO>> getOrganizationByAbbreviation(@PathVariable String abbreviation) {
        var organization = organizationService.getOrganizationByAbbreviation(abbreviation)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "abbreviation", abbreviation));
        OrganizationDTO organizationDTO = organizationMapper.toOrganizationDTO(organization);
        return ResponseUtil.success(organizationDTO, SUCCESS_MESSAGE);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<ApiResponse<Page<OrganizationDTO>>> getOrganizationsByType(
            @PathVariable String type,
            @RequestParam(defaultValue = DEFAULT_PAGE) @Min(MIN_DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) @Min(MIN_PAGE_SIZE) int size,
            @RequestParam(defaultValue = DEFAULT_SORT_FIELD) String sortBy,
            @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION) String sortDir) {

        Pageable pageable = ResponseUtil.buildPageable(page, size, sortBy, sortDir);

        OrganizationType organizationType;
        try {
            organizationType = OrganizationType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("INVALID_TYPE", "Invalid organization type: " + type));
        }

        Page<Organization> organizationPage = organizationService.getOrganizationsByType(organizationType, pageable);
        Page<OrganizationDTO> organizations = organizationPage.map(organizationMapper::toOrganizationDTO);

        return ResponseUtil.successWithPagination(organizations, SUCCESS_MESSAGE);
    }

    @GetMapping("/country/{countryId}")
    public ResponseEntity<ApiResponse<Page<OrganizationDTO>>> getOrganizationsByCountryId(
            @PathVariable Long countryId,
            @RequestParam(defaultValue = DEFAULT_PAGE) @Min(MIN_DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) @Min(MIN_PAGE_SIZE) int size,
            @RequestParam(defaultValue = DEFAULT_SORT_FIELD) String sortBy,
            @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION) String sortDir) {

        Pageable pageable = ResponseUtil.buildPageable(page, size, sortBy, sortDir);
        Page<Organization> organizationPage = organizationService.getOrganizationsByCountryId(countryId, pageable);
        Page<OrganizationDTO> organizationDTO = organizationPage.map(organizationMapper::toOrganizationDTO);

        return ResponseUtil.successWithPagination(organizationDTO, SUCCESS_MESSAGE);
    }

    @GetMapping("/parent/{parentId}")
    public ResponseEntity<ApiResponse<Page<OrganizationDTO>>> getOrganizationsByParentOrganizationId(
            @PathVariable Long parentId,
            @RequestParam(defaultValue = DEFAULT_PAGE) @Min(MIN_DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) @Min(MIN_PAGE_SIZE) int size,
            @RequestParam(defaultValue = DEFAULT_SORT_FIELD) String sortBy,
            @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION) String sortDir) {

        Pageable pageable = ResponseUtil.buildPageable(page, size, sortBy, sortDir);
        Page<Organization> organizationPage = organizationService.getOrganizationsByParentOrganizationId(parentId, pageable);
        Page<OrganizationDTO> organizationDTO = organizationPage.map(organizationMapper::toOrganizationDTO);

        return ResponseUtil.successWithPagination(organizationDTO, SUCCESS_MESSAGE);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<OrganizationDTO>>> searchOrganizationsByName(
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = DEFAULT_PAGE) @Min(MIN_DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) @Min(MIN_PAGE_SIZE) int size,
            @RequestParam(defaultValue = DEFAULT_SORT_FIELD) String sortBy,
            @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION) String sortDir) {

        Pageable pageable = ResponseUtil.buildPageable(page, size, sortBy, sortDir);
        Page<Organization> organizationPage = organizationService.searchOrganizationsByName(searchTerm, pageable);
        Page<OrganizationDTO> organizationDTO = organizationPage.map(organizationMapper::toOrganizationDTO);

        return ResponseUtil.successWithPagination(organizationDTO, SUCCESS_MESSAGE);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrganizationDTO>> createOrganization(@Valid @RequestBody OrganizationDTO organizationDTO) {
        try {
            Organization organization = organizationMapper.toOrganizationEntity(organizationDTO);
            Organization createdOrganization = organizationService.createOrganization(organization);
            OrganizationDTO createdOrganizationDTO = organizationMapper.toOrganizationDTO(createdOrganization);
            return ResponseUtil.created(createdOrganizationDTO, "Organization created successfully");
        } catch (DuplicateResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.error("DUPLICATE_ERROR", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(SERVER_ERROR, "Error creating organization: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrganizationDTO>> updateOrganization(
            @PathVariable Long id,
            @Valid @RequestBody OrganizationDTO organizationDTO) {
        try {
            Organization organization = organizationMapper.toOrganizationEntity(organizationDTO);
            Organization updatedOrganization = organizationService.updateOrganization(organization);
            OrganizationDTO updatedOrganizationDTO = organizationMapper.toOrganizationDTO(updatedOrganization);
            return ResponseUtil.success(updatedOrganizationDTO, "Organization updated successfully");
        } catch (ResourceNotFoundException e) {
            return ResponseUtil.notFound(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(SERVER_ERROR, "Error updating organization: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOrganization(@PathVariable Long id) {
        try {
            organizationService.deleteOrganization(id);
            return ResponseUtil.success(null, "Organization deleted successfully");
        } catch (ResourceNotFoundException e) {
            return ResponseUtil.notFound(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(SERVER_ERROR, "Error deleting organization: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<ApiResponse<Boolean>> organizationExists(@PathVariable Long id) {
        boolean exists = organizationService.existsById(id);
        return ResponseUtil.success(exists, "Organization existence check completed successfully");
    }

}
