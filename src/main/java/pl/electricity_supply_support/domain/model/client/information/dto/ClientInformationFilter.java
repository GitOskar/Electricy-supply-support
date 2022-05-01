package pl.electricity_supply_support.domain.model.client.information.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientInformationFilter {

    private String firstName;
    private String lastname;
    private BigDecimal serviceProvidedKwMin;
    private BigDecimal serviceProvidedKwMax;

    @Override
    public String toString() {
        return "ClientInformationFilter{" +
                "serviceProvidedKwMin=" + serviceProvidedKwMin +
                ", serviceProvidedKwMax=" + serviceProvidedKwMax +
                '}';
    }
}
