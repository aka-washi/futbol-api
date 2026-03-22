package com.eagle.futbolapi.features.registration.repository;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.registration.entity.Registration;

@Repository
public interface RegistrationRepository extends BaseRepository<Registration, Long> {

}
