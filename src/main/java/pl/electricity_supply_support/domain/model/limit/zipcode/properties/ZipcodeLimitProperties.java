package pl.electricity_supply_support.domain.model.limit.zipcode.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "zipcode-limit")
public class ZipcodeLimitProperties {

    @NotNull
    private BigDecimal defaultLimitKw;
}
