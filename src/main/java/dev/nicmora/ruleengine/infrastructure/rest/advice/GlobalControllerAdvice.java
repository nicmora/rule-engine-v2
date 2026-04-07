package dev.nicmora.ruleengine.infrastructure.rest.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import dev.nicmora.ruleengine.domain.exception.ResourceNotFoundException;
import dev.nicmora.ruleengine.infrastructure.rest.dto.response.ErrorResponse;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRuleNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(
            new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
            HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        return new ResponseEntity<>(
            new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred"
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}

