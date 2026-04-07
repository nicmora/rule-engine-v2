package dev.nicmora.ruleengine.infrastructure.rest.mapper;

import org.springframework.stereotype.Component;
import dev.nicmora.ruleengine.domain.model.Rule;
import dev.nicmora.ruleengine.infrastructure.rest.dto.request.RuleRequest;
import dev.nicmora.ruleengine.infrastructure.rest.dto.response.RuleResponse;

import java.util.List;

@Component
public class RuleMapper {

    public Rule toModel(RuleRequest ruleRequest) {
        return Rule.builder()
                .description(ruleRequest.getDescription())
                .type(ruleRequest.getType())
                .priority(ruleRequest.getPriority())
                .conditions(ruleRequest.getConditions())
                .enabled(ruleRequest.getEnabled())
                .build();
    }

    public RuleResponse toResponse(Rule rule) {
        return RuleResponse.builder()
                .id(rule.getId())
                .description(rule.getDescription())
                .type(rule.getType())
                .priority(rule.getPriority())
                .conditions(rule.getConditions())
                .resultType(rule.getResultType())
                .enabled(rule.getEnabled())
                .build();
    }

    public List<RuleResponse> toResponse(List<Rule> rules) {
        return rules.stream()
                .map(this::toResponse)
                .toList();
    }

}

