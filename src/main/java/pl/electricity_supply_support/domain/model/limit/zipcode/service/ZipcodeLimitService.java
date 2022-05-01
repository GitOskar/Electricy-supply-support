package pl.electricity_supply_support.domain.model.limit.zipcode.service;

import pl.electricity_supply_support.domain.model.limit.zipcode.dto.AddZipcodeLimitDto;
import pl.electricity_supply_support.domain.model.limit.zipcode.dto.ZipcodeLimitDto;

import java.math.BigDecimal;

public interface ZipcodeLimitService {
    ZipcodeLimitDto addZipcodeLimit(AddZipcodeLimitDto dto);
    BigDecimal findLimitByZipcode(String zipcode);
}
