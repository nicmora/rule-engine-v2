package dev.nicmora.ruleengine.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import dev.nicmora.ruleengine.domain.model.Rule;
import dev.nicmora.ruleengine.domain.repository.RuleRepository;

@Service
@RequiredArgsConstructor
public class CreateRule {

    private final RuleRepository ruleRepository;

    public Rule execute(Rule rule) {
        return ruleRepository.save(rule);
    }

}

