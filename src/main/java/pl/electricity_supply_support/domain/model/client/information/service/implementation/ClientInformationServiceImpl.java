package pl.electricity_supply_support.domain.model.client.information.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.electricity_supply_support.domain.model.client.information.dto.AddClientInformationDto;
import pl.electricity_supply_support.domain.model.client.information.dto.ClientInformationDto;
import pl.electricity_supply_support.domain.model.client.information.dto.ClientInformationFilter;
import pl.electricity_supply_support.domain.model.client.information.dto.UpdateClientInformationDto;
import pl.electricity_supply_support.domain.model.client.information.entity.ClientInformation;
import pl.electricity_supply_support.domain.model.client.information.exception.ClientInformationLimitExceedException;
import pl.electricity_supply_support.domain.model.client.information.repository.ClientInformationQueryDslRepository;
import pl.electricity_supply_support.domain.model.client.information.repository.ClientInformationRepository;
import pl.electricity_supply_support.domain.model.client.information.service.ClientInformationService;
import pl.electricity_supply_support.domain.model.exception.ApiException;
import pl.electricity_supply_support.domain.model.limit.zipcode.service.ZipcodeLimitService;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static java.util.Objects.isNull;
import static pl.electricity_supply_support.domain.model.client.information.mapper.ClientInformationMapper.mapClientInformationDtoToEntity;
import static pl.electricity_supply_support.domain.model.client.information.mapper.ClientInformationMapper.mapClientInformationEntityToDto;
import static pl.electricity_supply_support.domain.model.exception.ErrorStatus.CLIENT_LIMIT_EXCEED;
import static pl.electricity_supply_support.domain.model.exception.ErrorStatus.CLIENT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ClientInformationServiceImpl implements ClientInformationService {

    private final ClientInformationRepository clientInformationRepository;
    private final ClientInformationQueryDslRepository clientInformationQueryDslRepository;
    private final ZipcodeLimitService zipcodeLimitService;

    @Override
    public ClientInformationDto addClientInformation(AddClientInformationDto dto) {

        BigDecimal limit = zipcodeLimitService.findLimitByZipcode(dto.getServiceZipCode());
        BigDecimal sumOfServices = clientInformationRepository.findSumOfClientServices(dto.getFirstName(), dto.getLastName());

        if (isNull(sumOfServices)) {
            sumOfServices = ZERO;
        }

        validateLimit(dto.getServiceProvidedKw(), sumOfServices, limit);

        ClientInformation entity = mapClientInformationDtoToEntity(dto);
        entity = clientInformationRepository.save(entity);

        return mapClientInformationEntityToDto(entity);
    }

    @Override
    public ClientInformationDto updateClientInformation(UpdateClientInformationDto dto) {

        BigDecimal limit = zipcodeLimitService.findLimitByZipcode(dto.getServiceZipCode());
        BigDecimal sumOfServices = clientInformationRepository.findSumOfClientServicesWithout(dto.getFirstName(), dto.getLastName(), dto.getId());

        if (isNull(sumOfServices)) {
            sumOfServices = ZERO;
        }

        validateLimit(dto.getServiceProvidedKw(), sumOfServices, limit);

        ClientInformation entity = clientInformationRepository.findById(dto.getId())
                .orElseThrow(() -> new ApiException(CLIENT_NOT_FOUND));

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setServiceCountry(dto.getServiceCountry());
        entity.setServiceRegion(dto.getServiceRegion());
        entity.setServiceCity(dto.getServiceCity());
        entity.setServiceZipCode(dto.getServiceZipCode());
        entity.setServiceStreet(dto.getServiceStreet());
        entity.setHouseNumber(dto.getHouseNumber());
        entity.setApartmentNumber(dto.getApartmentNumber());
        entity.setServiceProvidedKw(dto.getServiceProvidedKw());
        entity.setNote((dto.getNote()));

        entity = clientInformationRepository.save(entity);

        return mapClientInformationEntityToDto(entity);
    }

    @Override
    public Page<ClientInformationDto> listClientInformation(ClientInformationFilter filter, Pageable pageable) {
        return clientInformationQueryDslRepository.findClientInformation(filter, pageable);
    }

    private void validateLimit(BigDecimal toAdd, BigDecimal sumOfServices, BigDecimal limit) {

        BigDecimal afterAdd = sumOfServices.add(toAdd);

        if (afterAdd.compareTo(limit) > 0) {
            BigDecimal exceedBy = afterAdd.subtract(limit);
            BigDecimal availableLimit = limit.subtract(sumOfServices);
            throw new ClientInformationLimitExceedException(CLIENT_LIMIT_EXCEED, exceedBy, availableLimit);
        }
    }
}
