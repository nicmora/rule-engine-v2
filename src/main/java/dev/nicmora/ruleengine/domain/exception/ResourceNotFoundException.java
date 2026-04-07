package dev.nicmora.ruleengine.domain.exception;

public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String msg) {
        super(msg);
    }

}

