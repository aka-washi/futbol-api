package com.eagle.futbolapi.features.country.controller;

import jakarta.validation.constraints.NotBlank;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.base.dto.ApiResponse;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.util.ResponseUtil;
import com.eagle.futbolapi.features.country.dto.CountryDTO;
import com.eagle.futbolapi.features.country.entity.Country;
import com.eagle.futbolapi.features.country.mapper.CountryMapper;
import com.eagle.futbolapi.features.country.service.CountryService;

@Validated
@RestController
@RequestMapping("/countries")
public class CountryController extends BaseCrudController<Country, CountryDTO, CountryService, CountryMapper> {

      private static final String RESOURCE_NAME = "Country";
    private static final String SUCCESS_MESSAGE = "Country retrieved successfully";
    private static final String DUPLICATE_MESSAGE = "Country already exists";
    private static final String SERVER_ERROR = "SERVER_ERROR";
  public CountryController(CountryService countryService, CountryMapper countryMapper) {
    super(
        countryService,
        countryMapper,
        RESOURCE_NAME,
        SUCCESS_MESSAGE,
        DUPLICATE_MESSAGE,
        SERVER_ERROR);
  }

  // Country-specific endpoints
  @GetMapping("/name/{name}")
  public ResponseEntity<ApiResponse<CountryDTO>> getCountryByName(@PathVariable String name) {
    Country country = service.getCountryByName(name)
        .orElseThrow(() -> new ResourceNotFoundException(resourceName, "name", name));
    CountryDTO countryDTO = mapper.toCountryDTO(country);
    return ResponseUtil.success(countryDTO, successMessage);
  }

  @GetMapping("/displayName/{displayName}")
  public ResponseEntity<ApiResponse<CountryDTO>> getCountryByDisplayName(@PathVariable String displayName) {
    Country country = service.getCountryByDisplayName(displayName)
        .orElseThrow(() -> new ResourceNotFoundException(resourceName, "displayName", displayName));
    CountryDTO countryDTO = mapper.toCountryDTO(country);
    return ResponseUtil.success(countryDTO, successMessage);
  }

  @GetMapping("/code/{code}")
  public ResponseEntity<ApiResponse<CountryDTO>> getCountryByCode(@PathVariable String code) {
    Country country = service.getCountryByCode(code)
        .orElseThrow(() -> new ResourceNotFoundException(resourceName, "code", code));
    CountryDTO countryDTO = mapper.toCountryDTO(country);
    return ResponseUtil.success(countryDTO, successMessage);
  }

  @GetMapping("/iso/{isoCode}")
  public ResponseEntity<ApiResponse<CountryDTO>> getCountryByIsoCode(@PathVariable @NotBlank String isoCode) {
    Country country = service.getCountryByIsoCode(isoCode)
        .orElseThrow(() -> new ResourceNotFoundException(resourceName, "isoCode", isoCode));
    CountryDTO countryDTO = mapper.toCountryDTO(country);
    return ResponseUtil.success(countryDTO, successMessage);
  }

  @Override
  protected Page<Country> getAllEntities(Pageable pageable) {
    return service.getAll(pageable);
  }

  @Override
  protected Country getEntityById(Long id) {
    return service.getById(id)
        .orElseThrow(() -> new ResourceNotFoundException(resourceName, "id", id));
  }

  @Override
  protected Country createEntity(CountryDTO dto) {
    return service.create(dto);
  }

  @Override
  protected Country updateEntity(Long id, CountryDTO dto) {
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
  protected CountryDTO toDTO(Country entity) {
    return mapper.toCountryDTO(entity);
  }

  @Override
  protected Country toEntity(CountryDTO dto) {
    return mapper.toCountry(dto);
  }
}
