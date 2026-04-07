package dev.nicmora.ruleengine.application.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import dev.nicmora.ruleengine.domain.exception.ResourceNotFoundException;
import dev.nicmora.ruleengine.domain.model.Evaluable;
import dev.nicmora.ruleengine.domain.model.Rule;
import dev.nicmora.ruleengine.domain.repository.RuleRepository;
import dev.nicmora.ruleengine.domain.service.Evaluator;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EvaluateElement {

    private final RuleRepository ruleRepository;

    public Rule evaluateElementWithRules(Evaluable evaluable) {
        String type = evaluable.getProcessType();
        log.debug("Searching rules for type: {}", type);
        List<Rule> rulesLst = ruleRepository.findByTypeAndEnabled(type, true);

        Rule ruleMatched = Optional.ofNullable(rulesLst)
                .filter(rules -> !rules.isEmpty())
                .map(rules -> Evaluator.evaluate(evaluable, rules))
                .orElseThrow(() -> {
                    log.error("No rules found for type: {}", type);
                    return new ResourceNotFoundException("No rules found for type: " + type);
                });

        return Optional.ofNullable(ruleMatched)
                .orElseThrow(() -> {
                    log.error("No matching rule found for evaluable: {}", evaluable);
                    return new ResourceNotFoundException("No matching rule found for evaluable: " + evaluable);
                });
    }

}

