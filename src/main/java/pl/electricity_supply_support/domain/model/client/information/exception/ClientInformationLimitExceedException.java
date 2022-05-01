package pl.electricity_supply_support.domain.model.client.information.exception;

import lombok.Getter;
import pl.electricity_supply_support.domain.model.exception.ApiException;
import pl.electricity_supply_support.domain.model.exception.ErrorStatus;

import java.math.BigDecimal;

@Getter
public class ClientInformationLimitExceedException extends ApiException {

    private final BigDecimal limitExceededBy;
    private final BigDecimal availableLimit;

    public ClientInformationLimitExceedException(ErrorStatus errorStatus, BigDecimal limitExceededBy, BigDecimal availableLimit) {
        super(errorStatus);
        this.limitExceededBy = limitExceededBy;
        this.availableLimit = availableLimit;
    }
}
