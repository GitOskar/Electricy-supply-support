package pl.electricity_supply_support.domain.model.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private final ErrorStatus errorStatus;

    public ApiException(ErrorStatus errorStatus) {
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
    }
}
