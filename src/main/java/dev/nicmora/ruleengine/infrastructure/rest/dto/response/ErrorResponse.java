package dev.nicmora.ruleengine.infrastructure.rest.dto.response;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private final int status;
    private final String message;
    private final LocalDateTime timestamp;
    private final Map<String, String> errors;

    public ErrorResponse(int status, String message) {
        this(status, message, null);
    }

    public ErrorResponse(int status, String message, Map<String, String> errors) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.errors = errors;
    }
}

