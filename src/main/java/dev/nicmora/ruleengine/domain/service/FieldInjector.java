package dev.nicmora.ruleengine.domain.service;

import lombok.extern.slf4j.Slf4j;
import dev.nicmora.ruleengine.domain.model.Evaluable;
import dev.nicmora.ruleengine.domain.model.Rule;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FieldInjector {

    private static final String INJECT_FIELDS_KEY = "injectFields";

    /**
     * Inyecta los campos de la regla ganadora en el evaluable.
     * Los campos existentes del evaluable se mantienen y se agregan los nuevos campos.
     *
     * @param evaluable El elemento evaluable al que se le inyectarán los campos
     * @param winningRule La regla ganadora que contiene los campos a inyectar
     * @return El evaluable con los campos inyectados
     */
    public static Evaluable injectFields(Evaluable evaluable, Rule winningRule) {
        if (evaluable == null || winningRule == null) {
            log.warn("Cannot inject fields: evaluable or winning rule is null");
            return evaluable;
        }

        Map<String, Object> ruleResult = winningRule.getResult();
        if (ruleResult == null || !ruleResult.containsKey(INJECT_FIELDS_KEY)) {
            log.info("No fields to inject from rule: {}", winningRule.getId());
            return evaluable;
        }

        Object injectFieldsObj = ruleResult.get(INJECT_FIELDS_KEY);
        if (!(injectFieldsObj instanceof Map)) {
            log.warn("injectFields is not a Map in rule: {}", winningRule.getId());
            return evaluable;
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> fieldsToInject = (Map<String, Object>) injectFieldsObj;

        Map<String, String> existingFields = evaluable.getFields();
        Map<String, String> newFields = new HashMap<>();

        if (existingFields != null) {
            newFields.putAll(existingFields);
        }

        fieldsToInject.forEach((key, value) -> {
            String stringValue = value != null ? value.toString() : null;
            newFields.put(key, stringValue);
            log.debug("Injected field: {} = {}", key, stringValue);
        });

        return Evaluable.builder()
                .id(evaluable.getId())
                .fields(newFields)
                .processType(evaluable.getProcessType())
                .productType(evaluable.getProductType())
                .build();
    }
}

