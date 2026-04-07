package dev.nicmora.ruleengine.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import dev.nicmora.ruleengine.domain.exception.ResourceNotFoundException;
import dev.nicmora.ruleengine.domain.repository.RuleRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteRule {
    private final RuleRepository ruleRepository;

    public void execute(UUID id) {
        if (!ruleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Rule not found with id: " + id);
        }
        ruleRepository.deleteById(id);
    }
}

