package dev.nicmora.ruleengine.domain.exception;

import lombok.Getter;

@Getter
public class BulkEvaluationException extends RuntimeException {

    private final int failedElementIndex;
    private final String failedElement;

    public BulkEvaluationException(String message, int failedElementIndex, String failedElement) {
        super(message);
        this.failedElementIndex = failedElementIndex;
        this.failedElement = failedElement;
    }

}


