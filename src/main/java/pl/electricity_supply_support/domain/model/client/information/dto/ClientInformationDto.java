package pl.electricity_supply_support.domain.model.client.information.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ClientInformationDto {

    private Long id;

    private String firstName;
    private String lastName;

    private String serviceCountry;
    private String serviceRegion;
    private String serviceCity;
    private String serviceZipCode;
    private String serviceStreet;
    private String houseNumber;
    private String apartmentNumber;

    private BigDecimal serviceProvidedKw;

    private String note;

    @QueryProjection
    public ClientInformationDto(Long id, String firstName, String lastName, String serviceCountry,
                                String serviceRegion, String serviceCity, String serviceZipCode, String serviceStreet,
                                String houseNumber, String apartmentNumber, BigDecimal serviceProvidedKw, String note) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.serviceCountry = serviceCountry;
        this.serviceRegion = serviceRegion;
        this.serviceCity = serviceCity;
        this.serviceZipCode = serviceZipCode;
        this.serviceStreet = serviceStreet;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;
        this.serviceProvidedKw = serviceProvidedKw;
        this.note = note;
    }

    @Override
    public String toString() {
        return "AddClientInformationDto{" +
                "serviceProvidedKw=" + serviceProvidedKw +
                '}';
    }
}
