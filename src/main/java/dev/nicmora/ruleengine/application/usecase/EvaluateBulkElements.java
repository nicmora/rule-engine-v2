package dev.nicmora.ruleengine.application.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import dev.nicmora.ruleengine.domain.exception.BulkEvaluationException;
import dev.nicmora.ruleengine.domain.model.Evaluable;
import dev.nicmora.ruleengine.domain.model.Rule;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EvaluateBulkElements {

    private final EvaluateElement evaluateElement;
    private final ObjectMapper objectMapper;

    public List<Rule> evaluateBulk(List<Evaluable> evaluables) {
        log.info("Starting bulk evaluation for {} elements", evaluables.size());

        List<Rule> matchedRules = new ArrayList<>();
        int index = 0;

        for (Evaluable evaluable : evaluables) {
            try {
                log.debug("Evaluating element at index {}", index);
                Rule matchedRule = evaluateElement.evaluateElementWithRules(evaluable);
                matchedRules.add(matchedRule);
            } catch (Exception e) {
                log.error("Error evaluating element at index {}: {}", index, e.getMessage(), e);
                String failedElementJson = convertToJson(evaluable);
                throw new BulkEvaluationException(
                        "Failed to evaluate element at index " + index + ": " + e.getMessage(),
                        index,
                        failedElementJson
                );
            }
            index++;
        }

        log.info("Bulk evaluation completed successfully. Total elements: {}", evaluables.size());

        return matchedRules;
    }

    private String convertToJson(Evaluable evaluable) {
        try {
            return objectMapper.writeValueAsString(evaluable);
        } catch (JsonProcessingException e) {
            log.error("Error converting evaluable to JSON: {}", e.getMessage());
            return evaluable.toString();
        }
    }

}


