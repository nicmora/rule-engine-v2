package dev.nicmora.ruleengine.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import dev.nicmora.ruleengine.domain.model.Condition;
import dev.nicmora.ruleengine.domain.model.Evaluable;
import dev.nicmora.ruleengine.domain.model.Rule;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class Evaluator {

    public static Rule evaluate(Evaluable evaluable, List<Rule> rules) {
        log.debug("Evaluating rules for evaluable: {}", evaluable);
        return rules.stream()
                .sorted(Comparator.comparing(Rule::getPriority))
                .filter(rule -> evalRule(evaluable, rule))
                .findFirst()
                .orElse(null);
    }

    private static boolean evalRule(Evaluable evaluable, Rule rule) {
        log.debug("Evaluating rule: {}", rule);
        return rule.getConditions()
                .stream()
                .allMatch(condition -> evalCondition(evaluable, condition));
    }

    private static boolean evalCondition(Evaluable evaluable, Condition condition) {
        Map<String, String> fields = evaluable.getFields();
        String field;
        String leftValue;
        String otherField;
        String rightValue;
        String value;

        switch (condition.getOperatorType()) {
            case VALUE -> {
                field = condition.getField();
                leftValue = Optional.ofNullable(fields.get(field)).orElse(Strings.EMPTY);
                rightValue = condition.getValue();
                return condition.getOperator().getStrategy().compare(leftValue, rightValue);
            }
            case FIELD -> {
                field = condition.getField();
                leftValue =  Optional.ofNullable(fields.get(field)).orElse(Strings.EMPTY);
                otherField = condition.getOtherField();
                rightValue = Optional.ofNullable(fields.get(otherField)).orElse(Strings.EMPTY);
                return condition.getOperator().getStrategy().compare(leftValue, rightValue);
            }
            case UNARY -> {
                field = condition.getField();
                value = fields.get(field);
                return condition.getOperator().getStrategy().compare(value, null);
            }
            default -> {
                log.error("Unsupported operator: {}", condition.getOperator());
                return false;
            }
        }
    }

}

