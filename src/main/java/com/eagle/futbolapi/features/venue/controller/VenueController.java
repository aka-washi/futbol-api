package com.eagle.futbolapi.features.venue.controller;

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
import com.eagle.futbolapi.features.venue.dto.VenueDTO;
import com.eagle.futbolapi.features.venue.entity.Venue;
import com.eagle.futbolapi.features.venue.mapper.VenueMapper;
import com.eagle.futbolapi.features.venue.service.VenueService;

@Validated
@RestController
@RequestMapping("/venues")
public class VenueController extends BaseCrudController<Venue, VenueDTO, VenueService, VenueMapper> {

  private static final String DEFAULT_PAGE = "0";
  private static final int MIN_DEFAULT_PAGE = 0;
  private static final String DEFAULT_PAGE_SIZE = "20";
  private static final int MIN_PAGE_SIZE = 1;
  private static final String DEFAULT_SORT_FIELD = "name";
  private static final String DEFAULT_SORT_DIRECTION = "asc";

  private static final String RESOURCE_NAME = "Venue";
  private static final String SUCCESS_MESSAGE = "Venue(s) retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Venue already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  public VenueController(VenueService venueService, VenueMapper venueMapper) {
    super(
        venueService,
        venueMapper,
        RESOURCE_NAME,
        SUCCESS_MESSAGE,
        DUPLICATE_MESSAGE,
        SERVER_ERROR);
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<ApiResponse<VenueDTO>> getVenueByName(@PathVariable String name) {
    var venue = service.getVenueByName(name)
        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "name", name));
    VenueDTO venueDTO = mapper.toDTO(venue);
    return ResponseUtil.success(venueDTO, SUCCESS_MESSAGE);
  }

  @GetMapping("/displayName/{displayName}")
  public ResponseEntity<ApiResponse<VenueDTO>> getVenueByDisplayName(@PathVariable String displayName) {
    var venue = service.getVenueByDisplayName(displayName)
        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "displayName", displayName));
    VenueDTO venueDTO = mapper.toDTO(venue);
    return ResponseUtil.success(venueDTO, SUCCESS_MESSAGE);
  }

  @GetMapping("/byCountry/{countryId}")
  public ResponseEntity<ApiResponse<Page<VenueDTO>>> getVenuesByCountryId(
      @PathVariable Long countryId,
      @RequestParam(defaultValue = DEFAULT_PAGE) @Min(MIN_DEFAULT_PAGE) int page,
      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) @Min(MIN_PAGE_SIZE) int size,
      @RequestParam(defaultValue = DEFAULT_SORT_FIELD) String sortField,
      @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION) String sortDir) {

    Pageable pageable = ResponseUtil.buildPageable(page, size, sortField, sortDir);

    Page<Venue> venuesPage = service.getVenuesByCountryId(countryId, pageable);
    Page<VenueDTO> venueDTOs = venuesPage.map(mapper::toDTO);
    return ResponseUtil.success(venueDTOs, SUCCESS_MESSAGE);
  }

  @Override
  protected Page<Venue> getAllEntities(Pageable pageable) {
    return service.getAll(pageable);
  }

  @Override
  protected Venue getEntityById(Long id) {
    return service.getById(id)
        .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "id", id));
  }

  @Override
  protected Venue createEntity(VenueDTO dto) {
    return service.create(dto);
  }

  @Override
  protected Venue updateEntity(Long id, VenueDTO dto) {
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
  protected VenueDTO toDTO(Venue entity) {
    return mapper.toDTO(entity);
  }

  @Override
  protected Venue toEntity(VenueDTO dto) {
    return mapper.toEntity(dto);
  }

}
