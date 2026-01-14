
package com.eagle.futbolapi.features.country.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.country.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    Optional<Country> findByCode(String code);

    Optional<Country> findByIsoCode(String isoCode);

    Optional<Country> findByName(String name);

    Optional<Country> findByDisplayName(String displayName);

    boolean existsByCode(String code);

    boolean existsByIsoCode(String isoCode);

    boolean existsByDisplayName(String displayName);
}
