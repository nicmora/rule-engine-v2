package dev.nicmora.ruleengine.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import dev.nicmora.ruleengine.domain.exception.ResourceNotFoundException;
import dev.nicmora.ruleengine.domain.model.Rule;
import dev.nicmora.ruleengine.domain.repository.RuleRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateRule {

    private final RuleRepository ruleRepository;

    public Rule execute(UUID id, Rule rule) {
        if (!ruleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Rule not found with id: " + id);
        }
        rule.setId(id);
        return ruleRepository.save(rule);
    }

}

