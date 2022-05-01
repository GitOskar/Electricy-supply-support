package pl.electricity_supply_support.domain.model.client.information.mapper;

import lombok.NoArgsConstructor;
import pl.electricity_supply_support.domain.model.client.information.dto.AddClientInformationDto;
import pl.electricity_supply_support.domain.model.client.information.dto.ClientInformationDto;
import pl.electricity_supply_support.domain.model.client.information.entity.ClientInformation;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ClientInformationMapper {

    public static ClientInformation mapClientInformationDtoToEntity(AddClientInformationDto dto) {

        return new ClientInformation(dto.getFirstName(),
                dto.getLastName(),
                dto.getServiceCountry(),
                dto.getServiceRegion(),
                dto.getServiceCity(),
                dto.getServiceZipCode(),
                dto.getServiceStreet(),
                dto.getHouseNumber(),
                dto.getApartmentNumber(),
                dto.getServiceProvidedKw(),
                dto.getNote());
    }

    public static ClientInformationDto mapClientInformationEntityToDto(ClientInformation entity) {
        return new ClientInformationDto(entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getServiceCountry(),
                entity.getServiceRegion(),
                entity.getServiceCity(),
                entity.getServiceZipCode(),
                entity.getServiceStreet(),
                entity.getHouseNumber(),
                entity.getApartmentNumber(),
                entity.getServiceProvidedKw(),
                entity.getNote());
    }
}
