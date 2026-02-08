package com.eagle.futbolapi.features.country.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.country.dto.CountryDto;
import com.eagle.futbolapi.features.country.entity.Country;
import com.eagle.futbolapi.features.country.mapper.CountryMapper;
import com.eagle.futbolapi.features.country.service.CountryService;

@Validated
@RestController
@RequestMapping("/countries")
public class CountryController extends BaseCrudController<Country, CountryDto, CountryService, CountryMapper> {

  private static final String RESOURCE_NAME = "Country";
  private static final String SUCCESS_MESSAGE = "Country retrieved successfully";
  private static final String DUPLICATE_MESSAGE = "Country already exists";
  private static final String SERVER_ERROR = "SERVER_ERROR";

  protected CountryController(CountryService service, CountryMapper mapper) {
    super(service, mapper, RESOURCE_NAME, SUCCESS_MESSAGE, DUPLICATE_MESSAGE, SERVER_ERROR);
  }

}
