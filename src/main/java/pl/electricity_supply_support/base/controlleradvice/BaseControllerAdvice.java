package pl.electricity_supply_support.base.controlleradvice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.electricity_supply_support.domain.model.client.information.exception.ClientInformationLimitExceedErrorResponse;
import pl.electricity_supply_support.domain.model.client.information.exception.ClientInformationLimitExceedException;
import pl.electricity_supply_support.domain.model.exception.ApiException;
import pl.electricity_supply_support.domain.model.exception.ErrorStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toCollection;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Slf4j
@ControllerAdvice
public class BaseControllerAdvice {

    @ExceptionHandler(ClientInformationLimitExceedException.class)
    public ResponseEntity<ClientInformationLimitExceedErrorResponse> handleApiException(ClientInformationLimitExceedException exception) {

        ErrorStatus errorStatus = exception.getErrorStatus();
        HttpStatus httpStatus = errorStatus.getHttpStatus();
        return new ResponseEntity<>(new ClientInformationLimitExceedErrorResponse(errorStatus.getMessage(),
                Integer.toString(httpStatus.value()), httpStatus.name(), exception.getLimitExceededBy(),
                exception.getAvailableLimit()), httpStatus);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiError> handleApiException(ApiException apiException) {

        ErrorStatus errorStatus = apiException.getErrorStatus();
        HttpStatus httpStatus = errorStatus.getHttpStatus();
        return new ResponseEntity<>(new ApiError(errorStatus.getMessage(), Integer.toString(httpStatus.value()), httpStatus.name()), httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        Map<String, List<String>> errors = exception.getBindingResult().getFieldErrors().stream()
                .collect(groupingBy(FieldError::getField, Collectors.mapping(DefaultMessageSourceResolvable::getDefaultMessage, toCollection(ArrayList::new))));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException) {

        return ResponseEntity.unprocessableEntity()
                .body(new ApiError("Parsing exception occurred request reading", Integer.toString(UNPROCESSABLE_ENTITY.value()), UNPROCESSABLE_ENTITY.name()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleOtherException(Exception exception) {
        log.error("Unexpected exception occurred:", exception);
        return ResponseEntity.internalServerError()
                .body(new ApiError("Internal server error", Integer.toString(INTERNAL_SERVER_ERROR.value()), INTERNAL_SERVER_ERROR.name()));
    }
}
