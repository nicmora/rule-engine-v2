package dev.nicmora.ruleengine.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import dev.nicmora.ruleengine.domain.service.GenericValueComparator;
import dev.nicmora.ruleengine.domain.service.OperatorStrategy;

@Slf4j
@Getter
@AllArgsConstructor
public enum Operator {

    EQUAL(GenericValueComparator::equalsSemantic),

    NOT_EQUAL((left, right) -> !GenericValueComparator.equalsSemantic(left, right)),

    GREATER_THAN((left, right) -> GenericValueComparator.compare(left, right) > 0),

    GREATER_THAN_OR_EQUAL_TO((left, right) -> GenericValueComparator.compare(left, right) >= 0),

    LESS_THAN((left, right) -> GenericValueComparator.compare(left, right) < 0),

    LESS_THAN_OR_EQUAL_TO((left, right) -> GenericValueComparator.compare(left, right) <= 0),

    IS_NULL((left, right) -> left == null),

    IS_NOT_NULL((left, right) -> left != null),

    IS_EMPTY((left, right) -> left != null && left.isEmpty()),

    IS_NOT_EMPTY((left, right) -> left != null && !left.isEmpty()),

    IS_BLANK((left, right) -> left == null || left.isBlank()),

    IS_NOT_BLANK((left, right) -> left != null && !left.isBlank());

    private final OperatorStrategy strategy;

}

