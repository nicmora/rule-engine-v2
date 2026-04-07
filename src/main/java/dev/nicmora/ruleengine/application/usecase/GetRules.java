package dev.nicmora.ruleengine.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import dev.nicmora.ruleengine.domain.model.Rule;
import dev.nicmora.ruleengine.domain.repository.RuleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetRules {

    private final RuleRepository ruleRepository;

    public List<Rule> execute() {
        return ruleRepository.findAll();
    }

}

