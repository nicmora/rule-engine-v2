package dev.nicmora.ruleengine.infrastructure.rest.mapper;

import org.springframework.stereotype.Component;
import dev.nicmora.ruleengine.domain.model.Evaluable;
import dev.nicmora.ruleengine.domain.model.Rule;
import dev.nicmora.ruleengine.infrastructure.rest.dto.request.EvaluableRequest;
import dev.nicmora.ruleengine.infrastructure.rest.dto.response.EvaluableResponse;

@Component
public class EvaluableMapper {
    
    public Evaluable toModel(EvaluableRequest request) {
        return Evaluable.builder()
                .id(request.getId())
                .fields(request.getFields())
                .processType(request.getProcessType())
                .productType(request.getProductType())
                .build();
    }

    public EvaluableResponse toResponse(Evaluable evaluable, Rule rule) {
        return EvaluableResponse.builder()
                .id(evaluable.getId())
                .fields(evaluable.getFields())
                .processType(evaluable.getProcessType())
                .productType(evaluable.getProductType())
                .matchedRule(rule)
                .build();
    }

}

