package pl.electricity_supply_support.domain.model.client.information.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.electricity_supply_support.domain.model.client.information.dto.AddClientInformationDto;
import pl.electricity_supply_support.domain.model.client.information.dto.ClientInformationDto;
import pl.electricity_supply_support.domain.model.client.information.dto.ClientInformationFilter;
import pl.electricity_supply_support.domain.model.client.information.dto.UpdateClientInformationDto;

public interface ClientInformationService {

    ClientInformationDto addClientInformation(AddClientInformationDto dto);

    ClientInformationDto updateClientInformation(UpdateClientInformationDto dto);

    Page<ClientInformationDto> listClientInformation(ClientInformationFilter filter, Pageable pageable);
}
