package dev.nicmora.ruleengine.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import dev.nicmora.ruleengine.domain.exception.ResourceNotFoundException;
import dev.nicmora.ruleengine.domain.model.Rule;
import dev.nicmora.ruleengine.domain.repository.RuleRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetRule {

    private final RuleRepository ruleRepository;

    public Rule execute(UUID id) {
        return ruleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rule not found with id: " + id));
    }

}

