package pl.electricity_supply_support.domain.model.limit.zipcode.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.electricity_supply_support.domain.model.limit.zipcode.enumerate.ZipcodeLimitType;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZipcodeLimitDto {

    private Long id;
    private String pattern;
    private ZipcodeLimitType limitType;
    private BigDecimal limitValueKw;
}
