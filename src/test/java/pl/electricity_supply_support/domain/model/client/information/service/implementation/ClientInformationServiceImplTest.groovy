package pl.electricity_supply_support.domain.model.client.information.service.implementation

import pl.electricity_supply_support.domain.model.client.information.dto.AddClientInformationDto
import pl.electricity_supply_support.domain.model.client.information.dto.ClientInformationDto
import pl.electricity_supply_support.domain.model.client.information.dto.UpdateClientInformationDto
import pl.electricity_supply_support.domain.model.client.information.entity.ClientInformation
import pl.electricity_supply_support.domain.model.client.information.exception.ClientInformationLimitExceedException
import pl.electricity_supply_support.domain.model.client.information.repository.ClientInformationQueryDslRepository
import pl.electricity_supply_support.domain.model.client.information.repository.ClientInformationRepository
import pl.electricity_supply_support.domain.model.client.information.service.ClientInformationService
import pl.electricity_supply_support.domain.model.limit.zipcode.service.ZipcodeLimitService
import spock.lang.Specification

class ClientInformationServiceImplTest extends Specification {

    ClientInformationRepository clientInformationRepository = Mock(ClientInformationRepository)
    ClientInformationQueryDslRepository clientInformationQueryDslRepository = Mock(ClientInformationQueryDslRepository)
    ZipcodeLimitService zipcodeLimitService = Mock(ZipcodeLimitService)
    ClientInformationService clientInformationService = new ClientInformationServiceImpl(clientInformationRepository, clientInformationQueryDslRepository, zipcodeLimitService)

    def "should correctly add client information"() {

        given:
        String firstName = "John"
        String lastName = "Doe"
        String zipcode = "20-200"
        AddClientInformationDto addClientInformationDto = new AddClientInformationDto(firstName, lastName, "PL", "Region", "City", zipcode, "Street", "12", "31", serviceToAdd, null)
        1 * zipcodeLimitService.findLimitByZipcode(zipcode) >> limit
        1 * clientInformationRepository.findSumOfClientServices(firstName, lastName) >> sumOfClientServices
        1 * clientInformationRepository.save(_ as ClientInformation) >> { entity -> entity[0] }

        when:
        def result = clientInformationService.addClientInformation(addClientInformationDto)

        then:
        def expectedResult = new ClientInformationDto(null, firstName, lastName, "PL", "Region", "City", zipcode, "Street", "12", "31", serviceToAdd, null)
        expectedResult == result

        where:
        serviceToAdd            | limit                      | sumOfClientServices
        BigDecimal.valueOf(105) | BigDecimal.valueOf(1005L)  | BigDecimal.valueOf(900L)
        BigDecimal.valueOf(105) | BigDecimal.valueOf(1006L)  | BigDecimal.valueOf(900L)
        BigDecimal.valueOf(200) | BigDecimal.valueOf(99999L) | BigDecimal.valueOf(5500L)
        BigDecimal.valueOf(400) | BigDecimal.valueOf(6201)   | BigDecimal.valueOf(5801L)
    }

    def "should correctly update client information"() {

        given:
        Long clientInformationId = 1L
        String firstName = "John"
        String lastName = "Doe"
        String zipcode = "20-200"
        UpdateClientInformationDto updateClientInformationDto = new UpdateClientInformationDto(clientInformationId, firstName, lastName, "PL", "Region", "City", zipcode, "Street", "12", "31", serviceToAdd, null)
        1 * zipcodeLimitService.findLimitByZipcode(zipcode) >> limit
        1 * clientInformationRepository.findSumOfClientServicesWithout(firstName, lastName, clientInformationId) >> sumOfClientServices
        1 * clientInformationRepository.findById(clientInformationId) >> Optional.of(new ClientInformation())
        1 * clientInformationRepository.save(_ as ClientInformation) >> { entity -> entity[0] }

        when:
        def result = clientInformationService.updateClientInformation(updateClientInformationDto)

        then:
        def expectedResult = new ClientInformationDto(null, firstName, lastName, "PL", "Region", "City", zipcode, "Street", "12", "31", serviceToAdd, null)
        expectedResult == result

        where:
        serviceToAdd            | limit                      | sumOfClientServices
        BigDecimal.valueOf(105) | BigDecimal.valueOf(1005L)  | BigDecimal.valueOf(900L)
        BigDecimal.valueOf(105) | BigDecimal.valueOf(1006L)  | BigDecimal.valueOf(900L)
        BigDecimal.valueOf(200) | BigDecimal.valueOf(99999L) | BigDecimal.valueOf(5500L)
        BigDecimal.valueOf(400) | BigDecimal.valueOf(6201)   | BigDecimal.valueOf(5801L)
    }


    def "should throw limit exceeded when adding client information"() {

        given:
        String firstName = "John"
        String lastName = "Doe"
        String zipcode = "20-200"
        AddClientInformationDto addClientInformationDto = new AddClientInformationDto(firstName, lastName, "PL", "Region", "City", zipcode, "Street", "12", "31", serviceToAdd, null)
        1 * zipcodeLimitService.findLimitByZipcode(zipcode) >> limit
        1 * clientInformationRepository.findSumOfClientServices(firstName, lastName) >> sumOfClientServices
        0 * clientInformationRepository.save(_)

        when:
        clientInformationService.addClientInformation(addClientInformationDto)

        then:
        def exception = thrown(ClientInformationLimitExceedException)
        exception.getAvailableLimit() == avaiableLimit
        exception.getLimitExceededBy() == limitExceededBy

        where:
        serviceToAdd            | limit                     | sumOfClientServices       || avaiableLimit | limitExceededBy
        BigDecimal.valueOf(105) | BigDecimal.valueOf(1000L) | BigDecimal.valueOf(900L)  || 100           | 5
        BigDecimal.valueOf(200) | BigDecimal.valueOf(5500L) | BigDecimal.valueOf(5500L) || 0             | 200
        BigDecimal.valueOf(400) | BigDecimal.valueOf(6200)  | BigDecimal.valueOf(5801L) || 399           | 1
    }

    def "should throw limit exceeded when updating client information"() {

        given:
        Long clientInformationId = 1L
        String firstName = "John"
        String lastName = "Doe"
        String zipcode = "20-200"
        UpdateClientInformationDto updateClientInformationDto = new UpdateClientInformationDto(clientInformationId, firstName, lastName, "PL", "Region", "City", zipcode, "Street", "12", "31", serviceToAdd, null)
        1 * zipcodeLimitService.findLimitByZipcode(zipcode) >> limit
        1 * clientInformationRepository.findSumOfClientServicesWithout(firstName, lastName, 1) >> sumOfClientServices
        0 * clientInformationRepository.save(_)

        when:
        clientInformationService.updateClientInformation(updateClientInformationDto)

        then:
        def exception = thrown(ClientInformationLimitExceedException)
        exception.getAvailableLimit() == avaiableLimit
        exception.getLimitExceededBy() == limitExceededBy

        where:
        serviceToAdd            | limit                     | sumOfClientServices       || avaiableLimit | limitExceededBy
        BigDecimal.valueOf(105) | BigDecimal.valueOf(1000L) | BigDecimal.valueOf(900L)  || 100           | 5
        BigDecimal.valueOf(200) | BigDecimal.valueOf(5500L) | BigDecimal.valueOf(5500L) || 0             | 200
        BigDecimal.valueOf(400) | BigDecimal.valueOf(6200)  | BigDecimal.valueOf(5801L) || 399           | 1
    }
}
