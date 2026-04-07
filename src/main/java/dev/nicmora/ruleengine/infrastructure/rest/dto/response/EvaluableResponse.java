package dev.nicmora.ruleengine.infrastructure.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import dev.nicmora.ruleengine.domain.model.Rule;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvaluableResponse {

    private UUID id;
    private Map<String, String> fields;
    private String processType;
    private String productType;
    private Rule matchedRule;

}

