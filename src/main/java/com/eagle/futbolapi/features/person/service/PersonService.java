package com.eagle.futbolapi.features.person.service;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.person.entity.Person;

@Service
@Transactional
public class PersonService extends BaseCrudService<Person, Long> {

}
