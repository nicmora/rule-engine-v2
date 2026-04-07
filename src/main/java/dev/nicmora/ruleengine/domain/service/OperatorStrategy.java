package dev.nicmora.ruleengine.domain.service;

public interface OperatorStrategy {

    boolean compare(String leftValue, String rightValue);

}
