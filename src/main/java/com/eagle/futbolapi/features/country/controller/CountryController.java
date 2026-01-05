package com.eagle.futbolapi.features.country.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.country.dto.CountryDTO;
import com.eagle.futbolapi.features.country.entity.Country;
import com.eagle.futbolapi.features.country.mapper.CountryMapper;
import com.eagle.futbolapi.features.country.service.CountryService;
import com.eagle.futbolapi.features.shared.ApiResponse;
import com.eagle.futbolapi.features.shared.ResponseUtil;
import com.eagle.futbolapi.features.shared.controller.BaseCrudController;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;

import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/countries")
@Validated
public class CountryController extends BaseCrudController<Country, CountryDTO, CountryService, CountryMapper> {

    public CountryController(CountryService countryService, CountryMapper countryMapper) {
        super(
            countryService,
            countryMapper,
            "Country",
            "Country retrieved successfully",
            "Country already exists",
            "SERVER_ERROR"
        );
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

    // Implement abstract methods from BaseCrudController
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
    protected Country createEntity(Country entity) {
        return service.create(entity);
    }

    @Override
    protected Country updateEntity(Long id, Country entity) {
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
    protected CountryDTO toDTO(Country entity) {
        return mapper.toCountryDTO(entity);
    }

    @Override
    protected Country toEntity(CountryDTO dto) {
        return mapper.toCountry(dto);
    }
}
