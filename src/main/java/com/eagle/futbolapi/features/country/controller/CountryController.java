package com.eagle.futbolapi.features.country.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

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

import com.eagle.futbolapi.features.country.dto.CountryDTO;
import com.eagle.futbolapi.features.country.entity.Country;
import com.eagle.futbolapi.features.country.mapper.CountryMapper;
import com.eagle.futbolapi.features.country.service.CountryService;
import com.eagle.futbolapi.features.shared.ApiResponse;
import com.eagle.futbolapi.features.shared.ResponseUtil;
import com.eagle.futbolapi.features.shared.exception.DuplicateResourceException;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

/**
 * REST controller for managing countries.
 * Provides endpoints for CRUD operations on country entities.
 */
@RestController
@RequestMapping("/countries")
@RequiredArgsConstructor
@Validated
public class CountryController {

    private static final String DEFAULT_SORT_FIELD = "name";
    private static final String DEFAULT_SORT_DIRECTION = "asc";
    private static final String DEFAULT_PAGE = "0";
    private static final int MIN_DEFAULT_PAGE = 0;
    private static final String DEFAULT_PAGE_SIZE = "20";
    private static final int MIN_PAGE_SIZE = 1;
    private static final String RESOURCE_NAME = "Country";
    private static final String SUCCESS_MESSAGE = "Country retrieved successfully";
    private static final String SERVER_ERROR = "SERVER_ERROR";

    private final CountryService countryService;
    private final CountryMapper countryMapper;

    /**
     * Retrieves all countries with pagination and sorting.
     *
     * @param page    the page number (zero-based)
     * @param size    the page size
     * @param sortBy  the field to sort by
     * @param sortDir the sort direction (asc or desc)
     * @return paginated list of countries
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<CountryDTO>>> getAllCountries(
            @RequestParam(defaultValue = DEFAULT_PAGE) @Min(MIN_DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) @Min(MIN_PAGE_SIZE) int size,
            @RequestParam(defaultValue = DEFAULT_SORT_FIELD) String sortBy,
            @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION) String sortDir) {

        Pageable pageable = ResponseUtil.buildPageable(page, size, sortBy, sortDir);
        Page<Country> countryPage = countryService.getAllCountries(pageable);
        Page<CountryDTO> countries = countryPage.map(countryMapper::toCountryDTO);

        return ResponseUtil.successWithPagination(countries, SUCCESS_MESSAGE);
    }

    /**
     * Retrieves a country by its ID.
     *
     * @param id the country ID
     * @return the country details
     * @throws ResourceNotFoundException if country not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CountryDTO>> getCountryById(@PathVariable Long id) {
        Country country = countryService.getCountryById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "id", id));
        CountryDTO countryDTO = countryMapper.toCountryDTO(country);
        return ResponseUtil.success(countryDTO, SUCCESS_MESSAGE);
    }

    /**
     * Retrieves a country by its code.
     *
     * @param code the country code
     * @return the country details
     * @throws ResourceNotFoundException if country not found
     */
    @GetMapping("/code/{code}")
    public ResponseEntity<ApiResponse<CountryDTO>> getCountryByCode(@PathVariable String code) {
        Country country = countryService.getCountryByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "code", code));
        CountryDTO countryDTO = countryMapper.toCountryDTO(country);
        return ResponseUtil.success(countryDTO, SUCCESS_MESSAGE);
    }

    /**
     * Retrieves a country by its ISO code.
     *
     * @param isoCode the ISO country code
     * @return the country details
     * @throws ResourceNotFoundException if country not found
     */
    @GetMapping("/iso/{isoCode}")
    public ResponseEntity<ApiResponse<CountryDTO>> getCountryByIsoCode(@PathVariable @NotBlank String isoCode) {
        Country country = countryService.getCountryByIsoCode(isoCode)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, "isoCode", isoCode));
        CountryDTO countryDTO = countryMapper.toCountryDTO(country);
        return ResponseUtil.success(countryDTO, SUCCESS_MESSAGE);
    }

    /**
     * Creates a new country.
     *
     * @param countryDTO the country data
     * @return the created country
     */
    @PostMapping
    public ResponseEntity<ApiResponse<CountryDTO>> createCountry(@Valid @RequestBody CountryDTO countryDTO) {
        try {
            Country country = countryMapper.toCountry(countryDTO);
            Country savedCountry = countryService.createCountry(country);
            CountryDTO savedCountryDTO = countryMapper.toCountryDTO(savedCountry);
            return ResponseUtil.created(savedCountryDTO, "Country created successfully");
        } catch (DuplicateResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.error("DUPLICATE_ERROR", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(SERVER_ERROR, "Error creating country: " + e.getMessage()));
        }
    }

    /**
     * Updates an existing country.
     *
     * @param id         the country ID
     * @param countryDTO the updated country data
     * @return the updated country
     * @throws ResourceNotFoundException if country not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CountryDTO>> updateCountry(
            @PathVariable Long id,
            @Valid @RequestBody CountryDTO countryDTO) {
        try {
            Country country = countryMapper.toCountry(countryDTO);
            Country updatedCountry = countryService.updateCountry(id, country);
            CountryDTO updatedCountryDTO = countryMapper.toCountryDTO(updatedCountry);
            return ResponseUtil.success(updatedCountryDTO, "Country updated successfully");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("NOT_FOUND", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(SERVER_ERROR, "Error updating country: " + e.getMessage()));
        }
    }

    /**
     * Deletes a country.
     *
     * @param id the country ID
     * @return success response
     * @throws ResourceNotFoundException if country not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCountry(@PathVariable Long id) {
        try {
            countryService.deleteCountry(id);
            return ResponseUtil.success(null, "Country deleted successfully");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("NOT_FOUND", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(SERVER_ERROR, "Error deleting country: " + e.getMessage()));
        }
    }

    /**
     * Checks if a country exists.
     *
     * @param id the country ID
     * @return true if country exists, false otherwise
     */
    @GetMapping("/{id}/exists")
    public ResponseEntity<ApiResponse<Boolean>> checkCountryExists(@PathVariable Long id) {
        boolean exists = countryService.existsById(id);
        return ResponseUtil.success(exists, "Country existence check completed");
    }

}
