package pl.electricity_supply_support.domain.model.limit.zipcode.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.electricity_supply_support.base.entity.Auditable;
import pl.electricity_supply_support.domain.model.limit.zipcode.enumerate.ZipcodeLimitType;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ZipcodeLimit extends Auditable {

    private String pattern;
    private ZipcodeLimitType limitType;
    private BigDecimal limitValueKw;
}
