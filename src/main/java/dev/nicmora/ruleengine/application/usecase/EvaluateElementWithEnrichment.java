package dev.nicmora.ruleengine.application.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import dev.nicmora.ruleengine.domain.model.Evaluable;
import dev.nicmora.ruleengine.domain.model.Rule;
import dev.nicmora.ruleengine.domain.service.FieldInjector;

@Slf4j
@Service
@RequiredArgsConstructor
public class EvaluateElementWithEnrichment {

    private final EvaluateElement evaluateElement;

    public Evaluable evaluateElementWithRules(Evaluable evaluable) {
        Rule winningRule = evaluateElement.evaluateElementWithRules(evaluable);

        Evaluable enrichedEvaluable = FieldInjector.injectFields(evaluable, winningRule);
        log.debug("Fields injected into evaluable from winning rule: {}", winningRule.getId());

        return enrichedEvaluable;
    }

}

