package pl.electricity_supply_support.domain.model.limit.zipcode.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.electricity_supply_support.domain.model.limit.zipcode.enumerate.ZipcodeLimitType;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddZipcodeLimitDto {

    @NotEmpty
    private String pattern;
    @NotNull
    private ZipcodeLimitType limitType;
    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal limitValueKw;
}
