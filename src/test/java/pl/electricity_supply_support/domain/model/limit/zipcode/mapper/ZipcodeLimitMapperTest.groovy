package pl.electricity_supply_support.domain.model.limit.zipcode.mapper

import pl.electricity_supply_support.domain.model.limit.zipcode.dto.AddZipcodeLimitDto
import pl.electricity_supply_support.domain.model.limit.zipcode.dto.ZipcodeLimitDto
import pl.electricity_supply_support.domain.model.limit.zipcode.entity.ZipcodeLimit
import pl.electricity_supply_support.domain.model.limit.zipcode.enumerate.ZipcodeLimitType
import spock.lang.Specification

class ZipcodeLimitMapperTest extends Specification {

    def "should map to entity correctly"() {

        expect:
        ZipcodeLimitMapper.mapToEntity(addZipcodeLimitDto) == expectedZipcodeLimitEntity

        where:
        addZipcodeLimitDto                                                                       || expectedZipcodeLimitEntity
        new AddZipcodeLimitDto("20-120", ZipcodeLimitType.SPECIFIC, BigDecimal.valueOf(1000L))   || new ZipcodeLimit("20-120", ZipcodeLimitType.SPECIFIC, BigDecimal.valueOf(1000L))
        new AddZipcodeLimitDto("20", ZipcodeLimitType.GROUP, BigDecimal.valueOf(5550L))          || new ZipcodeLimit("20", ZipcodeLimitType.GROUP, BigDecimal.valueOf(5550L))
        new AddZipcodeLimitDto("20-000", ZipcodeLimitType.SPECIFIC, BigDecimal.valueOf(111000L)) || new ZipcodeLimit("20-000", ZipcodeLimitType.SPECIFIC, BigDecimal.valueOf(111000L))
    }

    def "should map to dto correctly"() {

        expect:
        ZipcodeLimitMapper.mapToZipcodeLimitDto(entity) == expectedDto

        where:
        entity                                                                             || expectedDto
        new ZipcodeLimit("20-120", ZipcodeLimitType.SPECIFIC, BigDecimal.valueOf(1000L))   || new ZipcodeLimitDto(null, "20-120", ZipcodeLimitType.SPECIFIC, BigDecimal.valueOf(1000L))
        new ZipcodeLimit("20", ZipcodeLimitType.GROUP, BigDecimal.valueOf(5550L))          || new ZipcodeLimitDto(null, "20", ZipcodeLimitType.GROUP, BigDecimal.valueOf(5550L))
        new ZipcodeLimit("20-000", ZipcodeLimitType.SPECIFIC, BigDecimal.valueOf(111000L)) || new ZipcodeLimitDto(null, "20-000", ZipcodeLimitType.SPECIFIC, BigDecimal.valueOf(111000L))
    }
}
