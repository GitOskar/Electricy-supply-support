package pl.electricity_supply_support.domain.model.limit.zipcode.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.electricity_supply_support.domain.model.limit.zipcode.dto.AddZipcodeLimitDto;
import pl.electricity_supply_support.domain.model.limit.zipcode.dto.ZipcodeLimitDto;
import pl.electricity_supply_support.domain.model.limit.zipcode.entity.ZipcodeLimit;
import pl.electricity_supply_support.domain.model.limit.zipcode.properties.ZipcodeLimitProperties;
import pl.electricity_supply_support.domain.model.limit.zipcode.repository.ZipcodeLimitRepository;
import pl.electricity_supply_support.domain.model.limit.zipcode.service.ZipcodeLimitService;
import pl.electricity_supply_support.util.ZipcodeUtil;

import java.math.BigDecimal;

import static java.util.Objects.isNull;
import static pl.electricity_supply_support.domain.model.limit.zipcode.enumerate.ZipcodeLimitType.GROUP;
import static pl.electricity_supply_support.domain.model.limit.zipcode.enumerate.ZipcodeLimitType.SPECIFIC;
import static pl.electricity_supply_support.domain.model.limit.zipcode.mapper.ZipcodeLimitMapper.mapToEntity;
import static pl.electricity_supply_support.domain.model.limit.zipcode.mapper.ZipcodeLimitMapper.mapToZipcodeLimitDto;

@Service
@RequiredArgsConstructor
public class ZipcodeLimitServiceImpl implements ZipcodeLimitService {

    private final ZipcodeLimitRepository zipcodeLimitRepository;
    private final ZipcodeLimitProperties zipcodeLimitProperties;

    @Override
    public ZipcodeLimitDto addZipcodeLimit(AddZipcodeLimitDto dto) {

        ZipcodeLimit entity = mapToEntity(dto);
        entity = zipcodeLimitRepository.save(entity);
        return mapToZipcodeLimitDto(entity);
    }

    @Override
    public BigDecimal findLimitByZipcode(String zipcode) {

        BigDecimal limitValue = zipcodeLimitRepository.findZipcodeLimitByPatternAndType(zipcode, SPECIFIC);

        if (isNull(limitValue)) {
            String zipcodeGroup = ZipcodeUtil.extractGroup(zipcode);
            limitValue = zipcodeLimitRepository.findZipcodeLimitByPatternAndType(zipcodeGroup, GROUP);

            if (isNull(limitValue)) {
                limitValue = zipcodeLimitProperties.getDefaultLimitKw();
            }
        }

        return limitValue;
    }
}
