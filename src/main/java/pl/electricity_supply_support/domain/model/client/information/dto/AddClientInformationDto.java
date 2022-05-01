package pl.electricity_supply_support.domain.model.client.information.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddClientInformationDto {

    @NotEmpty
    @Length(min = 5, max = 50)
    private String firstName;
    @NotEmpty
    @Length(min = 5, max = 50)
    private String lastName;

    @NotEmpty
    @Length(min = 5, max = 50)
    private String serviceCountry;
    @NotEmpty
    @Length(min = 5, max = 50)
    private String serviceRegion;
    @NotEmpty
    @Length(min = 5, max = 50)
    private String serviceCity;
    @NotEmpty
    @Length(min = 5, max = 7)
    // zip code has very complicated validation based on countries, I actually won't to analyze this at this moment
    private String serviceZipCode;
    @NotEmpty
    @Length(min = 5, max = 50)
    private String serviceStreet;
    @NotEmpty
    @Length(min = 1, max = 4)
    private String houseNumber;
    @Length(min = 1, max = 4)
    private String apartmentNumber;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal serviceProvidedKw;

    @Length(max = 512)
    private String note;

    @Override
    public String toString() {
        return "AddClientInformationDto{" +
                "serviceProvidedKw=" + serviceProvidedKw +
                '}';
    }
}
