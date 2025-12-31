package com.eagle.futbolapi.features.country.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.country.entity.Country;
import com.eagle.futbolapi.features.country.repository.CountryRepository;
import com.eagle.futbolapi.features.shared.exception.DuplicateResourceException;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.shared.service.BaseCrudService;

@Service
@Transactional
public class CountryService extends BaseCrudService<Country, Long> {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        super(countryRepository);
        this.countryRepository = countryRepository;
    }

    public Optional<Country> getCountryByCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Country code cannot be null or empty");
        }
        return countryRepository.findByCode(code);
    }

    public Optional<Country> getCountryByIsoCode(String isoCode) {
        if (isoCode == null || isoCode.trim().isEmpty()) {
            throw new IllegalArgumentException("ISO code cannot be null or empty");
        }
        return countryRepository.findByIsoCode(isoCode);
    }

    public Optional<Country> getCountryByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Country name cannot be null or empty");
        }
        return countryRepository.findByName(name);
    }

    public Optional<Country> getCountryByDisplayName(String displayName) {
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Country display name cannot be null or empty");
        }
        return countryRepository.findByDisplayName(displayName);
    }

    public Country createCountry(@NotNull Country country) {
        Objects.requireNonNull(country, "Country cannot be null");

        // Check for duplicate code
        if (country.getCode() != null && countryRepository.existsByCode(country.getCode())) {
            throw new DuplicateResourceException("Country with code '" + country.getCode() + "' already exists");
        }

        // Check for duplicate ISO code
        if (country.getIsoCode() != null && countryRepository.existsByIsoCode(country.getIsoCode())) {
            throw new DuplicateResourceException("Country with ISO code '" + country.getIsoCode() + "' already exists");
        }

        return Objects.requireNonNull(
                countryRepository.save(country),
                "Country save operation returned null - this should never happen");
    }

    public Country updateCountry(@NotNull Long id, @NotNull Country countryDetails) {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(countryDetails, "Country details cannot be null");

        Country existingCountry = countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country", "id", id));

        // Check for duplicate code only if it's being changed
        if (countryDetails.getCode() != null && !countryDetails.getCode().equals(existingCountry.getCode())) {
            countryRepository.findByCode(countryDetails.getCode())
                    .ifPresent(country -> {
                        throw new DuplicateResourceException(
                                "Country with code '" + countryDetails.getCode() + "' already exists");
                    });
        }

        // Check for duplicate ISO code only if it's being changed
        if (countryDetails.getIsoCode() != null && !countryDetails.getIsoCode().equals(existingCountry.getIsoCode())) {
            countryRepository.findByIsoCode(countryDetails.getIsoCode())
                    .ifPresent(country -> {
                        throw new DuplicateResourceException(
                                "Country with ISO code '" + countryDetails.getIsoCode() + "' already exists");
                    });
        }

        existingCountry.setName(countryDetails.getName());
        existingCountry.setCode(countryDetails.getCode());
        existingCountry.setIsoCode(countryDetails.getIsoCode());
        existingCountry.setDisplayName(countryDetails.getDisplayName());
        existingCountry.setFlagUrl(countryDetails.getFlagUrl());

        return Objects.requireNonNull(
                countryRepository.save(existingCountry),
                "Country update operation returned null - this should never happen");
    }

    @Override
    public Country update(Long id, Country country) {
        Country existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entity with given ID does not exist"));
        country.setCreatedAt(existing.getCreatedAt());
        country.setId(id);
        return super.update(id, country);
    }

    @Override
    protected boolean isDuplicate(@NotNull Country country) {
        Objects.requireNonNull(country, "Country cannot be null");

        // Check for duplicate code
        if (country.getCode() != null && countryRepository.existsByCode(country.getCode())) {
            return true;
        }

        // Check for duplicate ISO code
        return country.getIsoCode() != null && countryRepository.existsByIsoCode(country.getIsoCode());
    }

    @Override
    protected boolean isDuplicate(Long id, @NotNull Country country) {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(country, "Country details cannot be null");

        Country existingCountry = getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country", "id", id));

        // Check for duplicate code
        if (country.getCode() != null && !country.getCode().equals(existingCountry.getCode())) {
            return true;
        }

        // Check for duplicate ISO code
        return country.getIsoCode() != null && !country.getIsoCode().equals(existingCountry.getIsoCode());
    }
}
