package pl.electricity_supply_support.domain.model.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Getter
@ToString
@RequiredArgsConstructor
public enum ErrorStatus {

    // 404
    CLIENT_NOT_FOUND("Client not found", NOT_FOUND),

    // 422
    CLIENT_LIMIT_EXCEED("Client limit exceed", UNPROCESSABLE_ENTITY);

    private final String message;
    private final HttpStatus httpStatus;
}
