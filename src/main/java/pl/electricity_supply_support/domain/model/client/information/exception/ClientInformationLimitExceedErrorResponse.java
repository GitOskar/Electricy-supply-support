package pl.electricity_supply_support.domain.model.client.information.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientInformationLimitExceedErrorResponse {

    private String message;
    private String httpStatusCode;
    private String httpStatusMessage;
    private BigDecimal limitExceededBy;
    private BigDecimal availableLimit;
}
