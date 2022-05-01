package pl.electricity_supply_support.domain.model.limit.zipcode.mapper;

import lombok.NoArgsConstructor;
import pl.electricity_supply_support.domain.model.limit.zipcode.dto.AddZipcodeLimitDto;
import pl.electricity_supply_support.domain.model.limit.zipcode.dto.ZipcodeLimitDto;
import pl.electricity_supply_support.domain.model.limit.zipcode.entity.ZipcodeLimit;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ZipcodeLimitMapper {

    public static ZipcodeLimit mapToEntity(AddZipcodeLimitDto dto) {
        return new ZipcodeLimit(dto.getPattern(), dto.getLimitType(), dto.getLimitValueKw());
    }

    public static ZipcodeLimitDto mapToZipcodeLimitDto(ZipcodeLimit entity) {
        return new ZipcodeLimitDto(entity.getId(), entity.getPattern(), entity.getLimitType(), entity.getLimitValueKw());
    }
}
