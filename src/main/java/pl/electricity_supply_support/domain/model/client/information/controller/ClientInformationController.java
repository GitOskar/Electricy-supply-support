package pl.electricity_supply_support.domain.model.client.information.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.electricity_supply_support.domain.model.client.information.dto.AddClientInformationDto;
import pl.electricity_supply_support.domain.model.client.information.dto.ClientInformationDto;
import pl.electricity_supply_support.domain.model.client.information.dto.ClientInformationFilter;
import pl.electricity_supply_support.domain.model.client.information.dto.UpdateClientInformationDto;
import pl.electricity_supply_support.domain.model.client.information.service.ClientInformationService;

import javax.validation.Valid;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/client/information")
public class ClientInformationController {

    private final ClientInformationService clientInformationService;

    @PostMapping
    public ResponseEntity<ClientInformationDto> addClientInformation(@Valid @RequestBody AddClientInformationDto dto) {

        log.info("Add client information. Request: {}", dto);
        ClientInformationDto clientInformationDto = clientInformationService.addClientInformation(dto);
        return ResponseEntity.ok(clientInformationDto);
    }

    @PutMapping
    public ResponseEntity<ClientInformationDto> updateClientInformation(@Valid @RequestBody UpdateClientInformationDto dto) {

        log.info("Update client information. Request: {}", dto);
        ClientInformationDto clientInformationDto = clientInformationService.updateClientInformation(dto);
        return ResponseEntity.ok(clientInformationDto);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<ClientInformationDto>> searchClientInformation(@PageableDefault(sort = "id", direction = ASC) Pageable pageable,
                                                                              @Valid @RequestBody ClientInformationFilter filter) {
        log.info("Search client information with filter. Filter: {}", filter);
        Page<ClientInformationDto> clientInformationDtoPage = clientInformationService.listClientInformation(filter, pageable);
        return ResponseEntity.ok(clientInformationDtoPage);
    }
}
