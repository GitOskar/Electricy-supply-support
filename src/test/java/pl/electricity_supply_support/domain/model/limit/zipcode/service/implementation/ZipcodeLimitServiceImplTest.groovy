package pl.electricity_supply_support.domain.model.limit.zipcode.service.implementation

import pl.electricity_supply_support.domain.model.limit.zipcode.dto.AddZipcodeLimitDto
import pl.electricity_supply_support.domain.model.limit.zipcode.dto.ZipcodeLimitDto
import pl.electricity_supply_support.domain.model.limit.zipcode.entity.ZipcodeLimit
import pl.electricity_supply_support.domain.model.limit.zipcode.properties.ZipcodeLimitProperties
import pl.electricity_supply_support.domain.model.limit.zipcode.repository.ZipcodeLimitRepository
import pl.electricity_supply_support.domain.model.limit.zipcode.service.ZipcodeLimitService
import spock.lang.Specification

import static pl.electricity_supply_support.domain.model.limit.zipcode.enumerate.ZipcodeLimitType.GROUP
import static pl.electricity_supply_support.domain.model.limit.zipcode.enumerate.ZipcodeLimitType.SPECIFIC

class ZipcodeLimitServiceImplTest extends Specification {

    ZipcodeLimitProperties zipcodeLimitProperties = Mock(ZipcodeLimitProperties)
    ZipcodeLimitRepository zipcodeLimitRepository = Mock(ZipcodeLimitRepository)
    ZipcodeLimitService zipcodeLimitService = new ZipcodeLimitServiceImpl(zipcodeLimitRepository, zipcodeLimitProperties)

    def "should add zipcode limit with specific type correctly"() {

        given:
        AddZipcodeLimitDto addZipcodeLimitDto = new AddZipcodeLimitDto("20-200", SPECIFIC, BigDecimal.valueOf(1500))
        1 * zipcodeLimitRepository.save(_ as ZipcodeLimit) >> { entities -> entities[0] }

        when:
        def result = zipcodeLimitService.addZipcodeLimit(addZipcodeLimitDto)

        then:
        def expectedResult = new ZipcodeLimitDto(null, "20-200", SPECIFIC, BigDecimal.valueOf(1500))
        result == expectedResult
    }

    def "should add zipcode limit with group type correctly"() {

        given:
        AddZipcodeLimitDto addZipcodeLimitDto = new AddZipcodeLimitDto("20", GROUP, BigDecimal.valueOf(1500))
        1 * zipcodeLimitRepository.save(_ as ZipcodeLimit) >> { entities -> entities[0] }

        when:
        def result = zipcodeLimitService.addZipcodeLimit(addZipcodeLimitDto)

        then:
        def expectedResult = new ZipcodeLimitDto(null, "20", GROUP, BigDecimal.valueOf(1500))
        result == expectedResult
    }

    def "should find specific limit"() {
        given:
        String specificZipcode = "20-200"

        1 * zipcodeLimitRepository.findZipcodeLimitByPatternAndType(specificZipcode, SPECIFIC) >> BigDecimal.valueOf(1500L)
        0 * zipcodeLimitRepository.findZipcodeLimitByPatternAndType(_ as String, GROUP)
        0 * zipcodeLimitProperties.getDefaultLimitKw()

        when:
        def result = zipcodeLimitService.findLimitByZipcode(specificZipcode)

        then:
        result == BigDecimal.valueOf(1500L)
    }

    def "should find group limit"() {
        given:
        String specificZipcode = "20-200"

        1 * zipcodeLimitRepository.findZipcodeLimitByPatternAndType(specificZipcode, SPECIFIC) >> null
        1 * zipcodeLimitRepository.findZipcodeLimitByPatternAndType("20", GROUP) >> BigDecimal.valueOf(1200L)
        0 * zipcodeLimitProperties.getDefaultLimitKw()

        when:
        def result = zipcodeLimitService.findLimitByZipcode(specificZipcode)

        then:
        result == BigDecimal.valueOf(1200L)
    }

    def "should find default limit"() {
        given:
        String specificZipcode = "20-200"

        1 * zipcodeLimitRepository.findZipcodeLimitByPatternAndType(specificZipcode, SPECIFIC) >> null
        1 * zipcodeLimitRepository.findZipcodeLimitByPatternAndType("20", GROUP) >> null
        1 * zipcodeLimitProperties.getDefaultLimitKw() >> BigDecimal.valueOf(1000L)

        when:
        def result = zipcodeLimitService.findLimitByZipcode(specificZipcode)

        then:
        result == BigDecimal.valueOf(1000L)
    }
}
