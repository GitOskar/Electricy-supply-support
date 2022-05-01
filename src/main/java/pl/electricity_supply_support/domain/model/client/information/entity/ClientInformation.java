package pl.electricity_supply_support.domain.model.client.information.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import pl.electricity_supply_support.base.entity.Auditable;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ClientInformation extends Auditable {

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

    @Length(max = 512)
    private String note;
}
