package pl.electricity_supply_support.domain.model.limit.zipcode.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.electricity_supply_support.domain.model.limit.zipcode.dto.AddZipcodeLimitDto;
import pl.electricity_supply_support.domain.model.limit.zipcode.dto.ZipcodeLimitDto;
import pl.electricity_supply_support.domain.model.limit.zipcode.service.ZipcodeLimitService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/limit/zipcode")
public class ZipcodeLimitController {

    private final ZipcodeLimitService clientInformationService;

    @PostMapping
    public ResponseEntity<ZipcodeLimitDto> addZipcodeLimit(@Valid @RequestBody AddZipcodeLimitDto dto) {

        log.info("Add zipcode limit. Request: {}", dto);
        ZipcodeLimitDto clientInformationDto = clientInformationService.addZipcodeLimit(dto);
        return ResponseEntity.ok(clientInformationDto);
    }
}
